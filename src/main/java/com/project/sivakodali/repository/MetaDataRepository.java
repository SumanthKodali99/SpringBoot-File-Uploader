package com.project.sivakodali.repository;

import com.project.sivakodali.entity.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaDataRepository extends JpaRepository<MetaData, String> {
}
