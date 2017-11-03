package com.project.sivakodali.services;

import com.project.sivakodali.dto.MetaDataDto;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Log4j2
@Service
public class FileService {

    @Autowired
    MetaDataService metaDataService;

    /**
     * {@inheritDoc}
     */
    public void writeFile(String filePath, InputStream in, long size) throws IOException {
        OutputStream out = new FileOutputStream(filePath);

        if (isLargeFile(size)) IOUtils.copyLarge(in, out);
        else IOUtils.copy(in, out);

        out.close();
    }

    public FileSystemResource getFileByMetaDataId(String metaDataId) {
        MetaDataDto metaData = metaDataService.findMetaData(metaDataId);
        return new FileSystemResource(metaData.getFullPath());
    }

    public void loadFileForMetadata(InputStream inputStream, String metaDataId) throws IOException, FileUploadException {
        MetaDataDto metaData = metaDataService.findMetaData(metaDataId);
        String filePath = metaData.getFullPath();

        writeFile(filePath, inputStream, metaData.getSize());
    }

    private boolean isLargeFile(long size) {
        return size > 2 * 1024 * 1024;
    }
}
