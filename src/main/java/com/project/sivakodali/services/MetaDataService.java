package com.project.sivakodali.services;

import com.project.sivakodali.dto.MetaDataDto;
import com.project.sivakodali.entity.MetaData;
import com.project.sivakodali.repository.MetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class MetaDataService {

    @Autowired
    private Path rootDirectory;

    @Autowired
    MetaDataRepository metaDataRepository;

    public List<MetaDataDto> findMetaData(Integer page, Integer perPage) {
        Page<MetaData> all = metaDataRepository.findAll(new PageRequest(page, perPage));
        List<MetaData> modelList = all.getContent();

        return modelList.stream()
                .map(MetaDataDto::new)
                .collect(toList());
    }

    public MetaDataDto findMetaData(String id) {
        MetaDataDto found = null;
        try {
            MetaData entity = metaDataRepository.getOne(id);
            found = new MetaDataDto(entity);
        } catch (EntityNotFoundException ex) {}
        return found;
    }

    public String save(MetaDataDto dto) {
        if (dto.getId() == null || dto.getId().isEmpty()) dto.setId(UUID.randomUUID().toString());

        dto.setFullPath(getPathToStoreFile(dto.getId()));
        MetaData saved = metaDataRepository.saveAndFlush(new MetaData(dto));

        return saved != null ? saved.getId() : null;
    }

    private String getPathToStoreFile(String id) {
        return Paths.get(rootDirectory.toString(), id).toAbsolutePath().normalize().toString();
    }

    public HttpHeaders buildHeadersFor(String id) {
        MetaDataDto meta = findMetaData(id);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", meta.getName()));
        headers.add("Content-Type", meta.getContentType());
        headers.add("Content-Length", "" + meta.getSize());
        headers.add("Cache-Control", meta.getCacheControl());
        headers.add("Content-MD5", meta.getMd5Hash());

        if(meta.getContentEncoding() != null)
            headers.add("Content-Encoding", meta.getContentEncoding());
        if(meta.getContentLanguage() != null)
            headers.add("Content-Encoding", meta.getContentLanguage());

        meta.getCustom().forEach(headers::add);

        return headers;

    }
}
