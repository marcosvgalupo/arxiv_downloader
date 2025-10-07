package com.searchonmath.arxivdownloader.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
@Log4j2
public class FileHandler {

    @Value("${files.folder-out}")
    private String folderOutPath;

    @Value("${files.output-format}")
    private String outputFormat;

    @Value("${files.checkSum-algorithm}")
    private String checkSumAlgorithm;

    @PostConstruct
    private void postConstruct() throws IOException{
        Path path = Paths.get(folderOutPath);
        if(!Files.exists(path))
            Files.createDirectory(path);
    }

    public void writeFile(String name, String body) throws IOException {
        String filePath = folderOutPath + name.replace("/", "_BARRA_").replace("\\", "_BARRAINVERTIDA_") + outputFormat;
        Path path = Paths.get(filePath);
        byte[] data = body.getBytes("utf-8");
        Files.write(path, data, StandardOpenOption.CREATE);
        log.info("Data written to file {}", filePath);
    }

    public String md5CheckSum(InputStream data){
        try {
            MessageDigest md  = MessageDigest.getInstance(checkSumAlgorithm);
            DigestInputStream dis = new DigestInputStream(data, md);
            byte[] digest = md.digest();
            return new String(digest);
        } catch (NoSuchAlgorithmException e) {
            log.error("Invalid algorithm to generate checkSum file. Algorithm on config file: {}", checkSumAlgorithm);
        }
        return "";
    }

}
