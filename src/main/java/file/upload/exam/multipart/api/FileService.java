package file.upload.exam.multipart.api;

import file.upload.exam.multipart.domain.dto.ImgUploadResponse;
import file.upload.exam.multipart.domain.dto.PathAndTypeDTO;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private static final String LOCAL_PATH = "C:\\Users\\Student\\Desktop\\study\\imgs\\";
    private static final String HOME_PATH = "C:\\Users\\bae\\Desktop\\study\\imgs";

    private static final String STORE_NAME = "TestStore";

    public ImgUploadResponse uploadImage(final MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String path = LOCAL_PATH + uuid;
        FileData fileData = FileData.builder()
                .name(uuid)
                .type(file.getContentType())
                .filePath(path)
                .storeName(STORE_NAME)
                .build();


        file.transferTo(new File(path));

        if (fileData != null) {
            FileData savedFile = fileRepository.save(fileData);
            log.info("[{}]", savedFile.getName());
            return ImgUploadResponse.builder()
                    .fileName(savedFile.getName())
                    .build();
        } else {
            throw new IOException("파일을 업로드할 수 없습니다.");
        }
    }

    public SimpleImgResponse download(final String fileName) throws IOException {
        FileData fileData = fileRepository.findByName(fileName).orElseThrow(
                () -> new IllegalStateException("가져올 수 없는 상태")
        );

        String path = fileData.getFilePath();
        byte[] file = Files.readAllBytes(new File(path).toPath());

        SimpleImgResponse response = SimpleImgResponse.builder()
                .file(file)
                .contentType(fileData.getType())
                .build();

        return response;
    }

    public List<PathAndTypeDTO> findAllByStoreName(final String storeName) {
        List<FileData> allByStoreName = fileRepository.findAllByStoreName(storeName);

        return allByStoreName.stream().map(fileData -> PathAndTypeDTO.of(fileData)).collect(Collectors.toList());
    }
}
