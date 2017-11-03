package com.project.sivakodali.entity;

import com.project.sivakodali.dto.MetaDataDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "metadata", schema = "default")
public class MetaData {
    @Id
    private String id;

    @NotNull
    private String fullPath;

    @NotNull
    private String name;

    @NotNull
    private Long size;

    @NotNull
    private String md5Hash;

    private String cacheControl;

    @NotNull
    private String contentDisposition;

    private String contentEncoding;

    private String contentLanguage;

    @NotNull
    private String contentType;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> custom;

    private LocalDateTime created;

    private LocalDateTime updated;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
        updated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }

    public MetaData() {
        custom = new HashMap<>();
    }

    public MetaData(String id) {
        this();
        this.id = id;
    }

    public MetaData(MetaDataDto dto) {
        this();
        if (dto == null) return;
        BeanUtils.copyProperties(dto, this);
    }


    @Override
    public String toString() {
        return "MetaData{" +
                "id='" + id + '\'' +
                ", fullPath='" + fullPath + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", md5Hash='" + md5Hash + '\'' +
                ", cacheControl='" + cacheControl + '\'' +
                ", contentDisposition='" + contentDisposition + '\'' +
                ", contentEncoding='" + contentEncoding + '\'' +
                ", contentLanguage='" + contentLanguage + '\'' +
                ", contentType='" + contentType + '\'' +
                ", custom=" + custom +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
