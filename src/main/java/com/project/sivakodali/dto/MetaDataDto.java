package com.project.sivakodali.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.sivakodali.entity.MetaData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MetaDataDto {

    private String id;

    private String fullPath;

    private String name;

    private Long size;

    private String md5Hash;

    private String cacheControl;

    private String contentDisposition;

    private String contentEncoding;

    private String contentLanguage;

    private String contentType;

    private Map<String, String> custom;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    public MetaDataDto() {
        custom = new HashMap<>();
    }

    public MetaDataDto(String id) {
        this();
        this.id = id;
    }

    public MetaDataDto(MetaData model) {
        this();
        if (model == null) return;
        BeanUtils.copyProperties(model, this);
    }
}
