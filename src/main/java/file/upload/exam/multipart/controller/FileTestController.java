package file.upload.exam.multipart.controller;

import file.upload.exam.multipart.api.FileService;
import file.upload.exam.multipart.domain.dto.SimpleImgResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileTestController {

    private final FileService fileService;

    @PostMapping("/api/v1/files")
    public ResponseEntity<String> upload(@RequestParam(name = "file") final MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(fileService.uploadImage(file));
    }

    @GetMapping("/api/v1/files")
    public ResponseEntity<?> download(@RequestParam(name = "targetId") final Long targetId) throws IOException {
        SimpleImgResponse response = fileService.download(targetId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(response.getContentType()))
                .body(response.getFile());
    }
}
