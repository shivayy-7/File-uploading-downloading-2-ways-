package com.FileUpload_Download.sevice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.FileUpload_Download.entity.FileData;
import com.FileUpload_Download.entity.ImageData;
import com.FileUpload_Download.repository.FileDataRepository;
import com.FileUpload_Download.repository.StorageRepository;
import com.FileUpload_Download.util.ImageUtils;

@Service
public class StorageService {

	@Autowired
	private StorageRepository repository;
	
	@Autowired
	private FileDataRepository fileRepo;
	
	private final String FOLDER_PATH="D:/Trip/file_uplord/";
	
	public String uploadImage(MultipartFile file) throws IOException {
		System.out.println(file.getSize());
		ImageData imageData = repository.save(ImageData.builder()
				  .name(file.getOriginalFilename())
				  .type(file.getContentType())
				  .imageData(ImageUtils.compressImage(file.getBytes())).build());
		if(imageData!=null) {
			return "file uploaded successfully : "+file.getOriginalFilename();
		}
		return null;
	}
	
	public byte[] downloadImage(String fileName) {
	   Optional<ImageData> dbImageData = repository.findByName(fileName);
	   byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
	   return images;
	}
	
	public String uploadImageToFileSystem(MultipartFile file) throws IOException {
		String filePath=FOLDER_PATH+file.getOriginalFilename();
		
		FileData fileData = fileRepo.save(FileData.builder()
				                    .name(file.getOriginalFilename())
				                    .type(file.getContentType())
				                    .filePath(filePath).build());
		file.transferTo(new File(filePath));
		
		if(fileData!=null) {
			return "file uploaded successfully : "+filePath;
		}
		return null;
	}
	
	public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
		   Optional<FileData> fileData = fileRepo.findByName(fileName);
		   String filePath = fileData.get().getFilePath();
		   byte[] images = Files.readAllBytes(new File(filePath).toPath());
		   return images;
		}
	
}
