package file.upload.exam.multipart.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FileData {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String type;

    private String filePath;

    private Long targetId;
}
