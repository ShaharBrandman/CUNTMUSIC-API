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

    private boolean downloadTrack(final String ID) throws InterruptedException, NullPointerException, IOException {
        Process p = Runtime.getRuntime().exec(new String[]{"bash", scriptsPath, ID});
        Thread t = new Thread(() -> {
            int exitCode = 1;
            try {
                while(exitCode != 0) {
                    exitCode = p.waitFor();
                }
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.start();
        t.join();

        return new File(tracksPath + ID).exists();
    }

    @GetMapping("/stream/{ID}")
    public ResponseEntity<InputStreamResource> streamAudio(@PathVariable final String ID) throws IOException, FileNotFoundException, InterruptedException {
        //validing user input
        if (ID.length() != 11) {
            return ResponseEntity.notFound().build();
        }

        String filePath = tracksPath + ID + "/" + trackFileName;
        System.out.println(filePath);
        File audioFile = new File(filePath);

        if (!audioFile.exists() && !downloadTrack(ID)) {
            return ResponseEntity.notFound().build();
        }

        FileInputStream audioStream = new FileInputStream(audioFile);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + ID + "/" + trackFileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.valueOf("video/webm").toString());

        InputStreamResource resource = new InputStreamResource(audioStream);

        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(audioFile.length())
            .body(resource);
    }
}
