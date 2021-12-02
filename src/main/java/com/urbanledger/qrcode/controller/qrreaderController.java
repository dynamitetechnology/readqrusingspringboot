package com.urbanledger.qrcode.controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.urbanledger.qrcode.utils.Util;



@RestController
public class qrreaderController {

	 //Save the uploaded file to this folder
	@Value("${UPLOADED_FOLDER}")
	private String UPLOADED_FOLDER;
	
	@Autowired
	Util utils;
	
	
	@RequestMapping(value = "/uploadqr", method = RequestMethod.POST)
	public String login(@RequestParam("qrcode") MultipartFile file)  throws WriterException, IOException,
    NotFoundException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Gson gson = new Gson(); 
        if (file.isEmpty()) {
        	 map.put("400", "Please select a file to upload");
        	 return	gson.toJson(map); 
          
        }

        try {
        	
        	UUID uuid = UUID.randomUUID();

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path savepath = Paths.get(UPLOADED_FOLDER + uuid.toString() + file.getOriginalFilename());
            Files.write(savepath, bytes);
            
        
            // Encoding charset
            String charset = "UTF-8";
     
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<EncodeHintType,
                              ErrorCorrectionLevel>();
     
            hashMap.put(EncodeHintType.ERROR_CORRECTION,
                        ErrorCorrectionLevel.L);
     
          String data =  utils.readQRCode(savepath.toString(), charset, hashMap);
            
            map.put("200", data);
            return	gson.toJson(map); 
        } catch (IOException e) {
            e.printStackTrace();
             map.put("400", e.getMessage());
            return	gson.toJson(map); 
        }
	 
	}
	
}
