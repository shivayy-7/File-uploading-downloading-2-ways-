package com.FileUpload_Download.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FileUpload_Download.entity.ImageData;

public interface StorageRepository extends JpaRepository<ImageData, Long> {

	Optional<ImageData> findByName(String fileName);
}
