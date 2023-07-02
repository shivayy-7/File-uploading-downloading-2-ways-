package com.FileUpload_Download.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FileUpload_Download.entity.FileData;
import com.FileUpload_Download.entity.ImageData;

public interface FileDataRepository extends JpaRepository<FileData, Integer> {

	Optional<FileData> findByName(String fileName);

}
