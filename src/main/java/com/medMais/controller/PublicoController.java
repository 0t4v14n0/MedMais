package com.medMais.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/publico")
public class PublicoController {
	
	@GetMapping("/logo")
	public ResponseEntity<Resource> getLogo() {
	    Path logoPath = Paths.get("src/main/resources/static/img/logo192.png");
	    Resource resource = null;
		try {
			resource = new UrlResource(logoPath.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	    
	    return ResponseEntity.ok()
	        .contentType(MediaType.IMAGE_PNG)
	        .body(resource);
	}

}
