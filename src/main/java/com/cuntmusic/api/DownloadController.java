package com.cuntmusic.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DownloadController {
    @GetMapping("/download/{ID}")
    public ResponseEntity<Object> download(@PathVariable String ID) {
        return ResponseEntity.ok().body(null);
    }
}
