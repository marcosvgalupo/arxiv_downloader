package com.searchonmath.arxivprocessor.service;

import com.searchonmath.arxivglobal.database.repository.PaperRepository;
import com.searchonmath.arxivglobal.dto.ArxivPage;
import com.searchonmath.arxivprocessor.util.FileUtil;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
@Service
public class PageService {
    private final FileUtil fileUtil;
    private final PaperRepository paperRepository;

    @Autowired
    public PageService(FileUtil fileUtil, PaperRepository paperRepository) {
        this.fileUtil = fileUtil;
        this.paperRepository = paperRepository;
    }

    @Async("pagePool")
    public void process(ArxivPage page) {

        try {
            log.info("Starting to process Page {}", page.getId());
            String pageContent = fileUtil.readFile(page.getPath());

            Document doc = Jsoup.parse(pageContent);

            Elements mathTags = doc.getElementsByTag("math");

            mathTags.forEach(this::extractMathAndReplaceBySom);

            Safelist safelist = new Safelist().addAttributes("som", "hash");
            String cleanText = Jsoup.clean(doc.html(), safelist);

            paperRepository.savePaperCoSrc(page.getId(), cleanText.getBytes(StandardCharsets.UTF_8));
            log.info("Finishing process of Page {}", page.getId());
        } catch (IOException e) {
            log.error("Error when try read file {} / Exception message: {} / Exception cause: {}", page.getPath(), e.getMessage(), e.getCause());
        }
    }

    public void extractMathAndReplaceBySom(Element mathTag) {
        String latex = mathTag.attr("alttext");

        String latexHashed = fileUtil.sha256(latex);

        log.info("Processing formula {}", latexHashed);

        String prefix = latexHashed.substring(0, 3);

        Element mathml = mathTag.selectFirst("semantics");

        mathTag.select("annotation, annotation-xml").remove();

        Element fileContent = new Element("math")
                .attr("xmlns", "http://www.w3.org/1998/Math/MathML")
                .appendChild(mathml);

        try {
            fileUtil.writeFile(prefix, latexHashed, fileContent.outerHtml());
        } catch (IOException e) {
            log.error("Error when try write file extract from math tag. Latex: {} / Hash: {} / Exception: {}", latex, latexHashed, e.getCause());
        }

        Element somTag = new Element("som");

        somTag.attr("hash", latexHashed);

        somTag.html(latex);

        mathTag.replaceWith(somTag);

    }



}
