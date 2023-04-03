package file.upload.exam.multipart.domain.repository;

import file.upload.exam.multipart.domain.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileData, Long> {

    Optional<FileData> findByFilePath(final String filePath);

    List<FileData> findAllByStoreName(final String storeName);
}
