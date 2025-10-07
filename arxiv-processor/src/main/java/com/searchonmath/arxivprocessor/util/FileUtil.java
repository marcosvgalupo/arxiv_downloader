package com.searchonmath.arxivprocessor.util;

import com.google.common.hash.Hashing;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Component
public class FileUtil {
    @Value("${files.folder-out}")
    private String folderOutPath;

    @Value("${files.output-format}")
    private String outputFormat;

    @PostConstruct
    private void postConstruct() throws IOException {
        createDirectoryIfNotExist(folderOutPath);
    }

    private void createDirectoryIfNotExist(String dir) throws IOException{
        try{
            Path path = Paths.get(dir);
            if(!Files.exists(path)){
                Files.createDirectory(path);
            }
        }catch (FileAlreadyExistsException e){
            log.info("Directory {} already exists", dir);
        }

    }

    public void writeFile(String folder, String name, String content) throws IOException {
        createDirectoryIfNotExist(String.format("%s/%s", folderOutPath, folder));
        String filePath = String.format("%s/%s/%s.%s", folderOutPath, folder, name, outputFormat);
        Path path = Paths.get(filePath);
        if(!Files.exists(path)) {
            Files.writeString(path, content, StandardOpenOption.CREATE);
            log.trace("Data written to file {}", filePath);
        }else{
            log.trace("File for {} already exists", name);
        }
    }

    public String readFile(String path) throws IOException{
        Path filePath = Paths.get(path);
        Stream<String> lines = Files.lines(filePath);
        return lines.collect(Collectors.joining("\n"));
    }

    public String sha256(String value){
        return Hashing.sha256().hashString(value, StandardCharsets.UTF_8).toString();
    }

    public Boolean checkIfFileAlreadyExists(String folder, String name){
        String filePath = String.format("%s/%s/%s.%s", folderOutPath, folder, name, outputFormat);
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }
}
