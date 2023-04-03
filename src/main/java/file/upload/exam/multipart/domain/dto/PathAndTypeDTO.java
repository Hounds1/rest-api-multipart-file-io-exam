package file.upload.exam.multipart.domain.dto;

import file.upload.exam.multipart.domain.model.FileData;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PathAndTypeDTO {

    private String filePath;

    private String contentType;

    public static PathAndTypeDTO of(final FileData fileData) {
        return PathAndTypeDTO.builder()
                .filePath(fileData.getFilePath())
                .contentType(fileData.getType())
                .build();
    }
}
