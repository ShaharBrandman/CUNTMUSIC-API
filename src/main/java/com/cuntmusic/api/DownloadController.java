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

    private ResponseEntity<Resource> download(String filePath) {
        try {
            Path p = Paths.get(tracksPath).resolve(filePath).normalize();
            Resource r = new UrlResource(p.toUri());
            if (r.exists() || r.isReadable()) {
                return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + p.getFileName() + "\"")
                    .body(r);
            }
            
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    //custom feature for subtuiel
    // @GetMapping("/download/{ID}.{Lang}.vtt")
    // public ResponseEntity<Resource> subtitlesDownload(@PathVariable String ID, @PathVariable String Lang) {
    //     return download(ID + "/track." + Lang + ".vtt");
    // }

    //designed to handle .m4a, .webp
    @GetMapping("/download/{ID}.{Type}")
    public ResponseEntity<Resource> simpleDownload(@PathVariable String ID, @PathVariable String Type) {
        return download(ID + "/track." + Type);
    }
}
