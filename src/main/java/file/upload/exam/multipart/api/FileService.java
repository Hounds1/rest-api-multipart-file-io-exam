package file.upload.exam.multipart.api;

import file.upload.exam.multipart.domain.dto.ImgUploadResponse;
import file.upload.exam.multipart.domain.dto.SimpleImgResponse;
import file.upload.exam.multipart.domain.model.FileData;
import file.upload.exam.multipart.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private static final String LOCAL_PATH = "C:\\Users\\Student\\Desktop\\study\\imgs\\";
    private static final String HOME_PATH = "C:\\Users\\bae\\Desktop\\study\\imgs";

    private Long sequence = 1L;

    public ImgUploadResponse uploadImage(final MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String path = HOME_PATH + uuid;
        FileData fileData = FileData.builder()
                .name(uuid)
                .type(file.getContentType())
                .filePath(path)
                .targetId(sequence++)
                .build();


        file.transferTo(new File(path));

        if (fileData != null) {
            FileData savedFile = fileRepository.save(fileData);
            return ImgUploadResponse.builder()
                    .targetId(savedFile.getId())
                    .build();
        } else {
            throw new IOException("파일을 업로드할 수 없습니다.");
        }
    }

    public SimpleImgResponse download(final Long targetId) throws IOException {
        FileData fileData = fileRepository.findByTargetId(targetId).orElseThrow(
                () -> new IllegalStateException("가져올 수 없음")
        );

        String path = fileData.getFilePath();
        byte[] file = Files.readAllBytes(new File(path).toPath());

        SimpleImgResponse response = SimpleImgResponse.builder()
                .file(file)
                .contentType(fileData.getType())
                .build();

        return response;
    }
}
