package com.lyloou.practice.dao;

import com.lyloou.practice.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResumeDao extends JpaRepository<Resume, Long>,
        JpaSpecificationExecutor<Resume> {
}