package com.medMais.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.medMais.controller.dto.PixChargeRequest;
import com.medMais.domain.payment.efi.PixService;

@RestController
@RequestMapping("/pix")
public class PixEFIController {

    @Autowired
    private PixService pixService;

    @PostMapping
    public ResponseEntity<?> pixCreateCharge(@RequestBody PixChargeRequest pixChargeRequest,
			  											  Authentication authentication){    	
    	
    	JSONObject response = pixService.pixCreateCharge(pixChargeRequest,authentication.getName());
    	
    	return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toString());
    }
}
