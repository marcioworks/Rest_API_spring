package com.marciobr.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.flywaydb.core.internal.authentication.mysql.MySQLOptionFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.marciobr.config.FileStorageConfig;
import com.marciobr.exceptions.FileStorageException;
import com.marciobr.exceptions.MyFileNotFoundException;



@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	
	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("could not create directory for storage files", e);
		}
	}
	
	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if(fileName.contains("..")) {
				throw new FileStorageException("fileName contains invalid sequence, "+ fileName);
			}
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (Exception e) {
			throw new FileStorageException("could not create storage file, please try again", e);
		}
	}
	
	public Resource loadFileAsResource(String filename) {
		try {
			Path filePath = this.fileStorageLocation.resolve(filename).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			}else {
				throw new MyFileNotFoundException("fileName contains invalid sequence, "+ filename);
			}
		} catch (Exception e) {
			throw new MyFileNotFoundException("fileName contains invalid sequence, "+ filename, e);
		}
	}
	
}
