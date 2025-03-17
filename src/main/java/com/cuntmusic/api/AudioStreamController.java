package com.cuntmusic.api;

import org.springframework.core.io.InputStreamResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

@RestController
public class AudioStreamController {

    @GetMapping("/stream/{ID}")
    public ResponseEntity<InputStreamResource> streamAudio(@PathVariable String ID) throws IOException, FileNotFoundException {
        String filePath = "/home/gingi/CUNTMUSIC-API/" + ID + "/track.webm";
        File audioFile = new File(filePath);

        if (!audioFile.exists()) {
            return ResponseEntity.status(404).build();
        }

        FileInputStream audioStream = new FileInputStream(audioFile);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + ID + "/track.webm");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.valueOf("video/webm").toString());

        InputStreamResource resource = new InputStreamResource(audioStream);

        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(audioFile.length())
            .body(resource);
    }
}
