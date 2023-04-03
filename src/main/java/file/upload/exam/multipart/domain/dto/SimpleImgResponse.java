package file.upload.exam.multipart.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SimpleImgResponse {

    private byte[] file;

    private String contentType;
}
