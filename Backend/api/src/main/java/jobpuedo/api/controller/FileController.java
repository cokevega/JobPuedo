package jobpuedo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jobpuedo.api.entity.FileDB;
import jobpuedo.api.service.FileService;

@RestController
public class FileController {

	@Autowired
	private FileService fileService;

	//Get file
	@GetMapping("/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		FileDB fileDB = fileService.getFile(id);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
				.body(fileDB.getData());
	}
}
