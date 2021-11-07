package com.marciobr.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marciobr.data.vo.UploadResponseVO;
import com.marciobr.services.FileStorageService;

import io.swagger.annotations.Api;

@Api(tags="fileEndpoint")
@RestController
@RequestMapping("/api/file")
public class FileController {

	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping("/uploadFile")
	public UploadResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
		String filename = fileStorageService.storeFile(file);
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/file/dowloadFile/")
				.path(filename)
				.toUriString();
		
		return new UploadResponseVO(filename, fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	@PostMapping("/uploadMultipleFile")
	public List<UploadResponseVO> uploadMultipleFile(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files)
				.stream()
				.map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}
}
