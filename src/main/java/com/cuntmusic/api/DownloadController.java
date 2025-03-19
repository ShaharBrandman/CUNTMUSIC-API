package com.cuntmusic.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.beans.factory.annotation.Value;

@Controller
public class DownloadController {

    @Value("${spring.application.tracksPath}")
    private String tracksPath;

    @GetMapping("/download/{ID}.{Type}")
    public ResponseEntity<Resource> download(@PathVariable String ID, @PathVariable String Type) {
        try {
            Path filePath = Paths.get(tracksPath).resolve(ID + "/track." + Type).normalize();
            Resource r = new UrlResource(filePath.toUri());
            if (r.exists() || r.isReadable()) {
                return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + ID + "." + Type + "\"")
                    .body(r);
            }
            
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
