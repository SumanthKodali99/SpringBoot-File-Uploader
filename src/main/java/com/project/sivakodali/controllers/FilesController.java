package com.project.sivakodali.controllers;

import com.project.sivakodali.services.FileService;
import com.project.sivakodali.services.MetaDataService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/files")
@Log4j2
public class FilesController {

    @Autowired
    FileService fileService;

    @Autowired
    MetaDataService metaDataService;

    @RequestMapping(value = "/{metaDataId}", method = GET)
    public ResponseEntity<FileSystemResource> get(@PathVariable String metaDataId) {
        log.info("Request file by ID=" + metaDataId);
        HttpHeaders headers = metaDataService.buildHeadersFor(metaDataId);
        FileSystemResource file = fileService.getFileByMetaDataId(metaDataId);
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{metaDataId}", method = POST)
    public ResponseEntity<String> upload(@PathVariable String metaDataId, @RequestParam("file") MultipartFile file) throws IOException, FileUploadException {
        log.info("Uploading file for metadata ID={}", metaDataId);
        try {
            fileService.loadFileForMetadata(file.getInputStream(), metaDataId);
        } catch (FileUploadException e) {
            log.error(e);
            return new ResponseEntity<>("File upload error", HttpStatus.PRECONDITION_FAILED);
        } catch (IOException e) {
            log.error(e);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("created", HttpStatus.OK);
    }
}


