package file.upload.exam.multipart.controller;

import file.upload.exam.multipart.api.FileService;
import file.upload.exam.multipart.domain.dto.ImgUploadResponse;
import file.upload.exam.multipart.domain.dto.PathAndTypeDTO;
import file.upload.exam.multipart.domain.dto.SimpleImgResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileTestController {

    private final FileService fileService;

    @PostMapping("/api/v1/files")
    public ResponseEntity<ImgUploadResponse> upload(@RequestParam(name = "file") final MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.uploadImage(file));
    }

    @GetMapping("/api/v1/files")
    public ResponseEntity<?> download(@RequestParam(name = "filePath") final String filePath) throws IOException {
        SimpleImgResponse response = fileService.download(filePath);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(response.getContentType()))
                .body(response.getFile());
    }

    @GetMapping("/api/v1/files/store")
    public ResponseEntity<List<PathAndTypeDTO>> getStorePath(@RequestParam(name = "storeName") final String storeName) {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.findAllByStoreName(storeName));
    }
}
