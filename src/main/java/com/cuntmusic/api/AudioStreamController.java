package com.cuntmusic.api;

import org.springframework.beans.factory.annotation.Value;

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

import java.lang.Process;
import java.lang.Thread;
import java.lang.NullPointerException;

@RestController
public class AudioStreamController {

    @Value("${spring.application.downloaderPath}")
    private String scriptsPath;

    @Value("${spring.application.tracksPath}")
    private String tracksPath;

    @Value("${spring.application.trackFileName}")
    private String trackFileName;

    private boolean downloadNewTrack(final String ID, final File tmpFile) throws InterruptedException, NullPointerException, IOException {
        Process p = Runtime.getRuntime().exec(new String[]{"bash", scriptsPath, ID});
        Thread t = new Thread(() -> {
            int exitCode = 1;
            try {
                while(exitCode != 0) exitCode = p.waitFor();
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.start();
        t.join();

        return tmpFile.exists();
    }
    @GetMapping("/stream/{ID}")
    public ResponseEntity<InputStreamResource> streamAudio(@PathVariable final String ID) throws IOException, FileNotFoundException, InterruptedException {
        //validing user input
        if (ID.length() != 11) {
            return ResponseEntity.notFound().build();
        }
            
        String filePath = tracksPath + ID + "/" + trackFileName;
        File audioFile = new File(filePath);
        
        if (!audioFile.exists()) {
            if (!downloadNewTrack(ID, audioFile)) {
                return ResponseEntity.notFound().build();
            }
            /*
             * HERE IT SHOULD ADD THE NEW SONG TO THE SQL DB
             */
        }
    
        //creating the HTTP headers for the audio stream
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + ID + "/" + trackFileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.valueOf("audio/m4a").toString());
            
        //creating the audio stream and injecting it into the HTTP resource
        InputStreamResource resource = new InputStreamResource(new FileInputStream(audioFile));

        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(audioFile.length())
            .body(resource);
    }
}
