package pl.kamilkubiak2210.filmvault.storage;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private final String imageStorageLocation;

    public FileStorageService(@Value("${app.storage.location}") String storageLocation) {
        String fileStorageLocation = storageLocation + "/files/";
        this.imageStorageLocation = storageLocation + "/img/";
        Path fileStoragePath = Path.of(fileStorageLocation);
        Path imageStoragePath = Path.of(this.imageStorageLocation);
        prepareStorageDirectories(fileStoragePath, imageStoragePath);
    }

    private void prepareStorageDirectories(Path fileStoragePath, Path imageStoragePath) {
        try {
            if (Files.notExists(fileStoragePath)) {
                Files.createDirectories(fileStoragePath);
                logger.info("File storage directory created %s".formatted(fileStoragePath.toAbsolutePath().toString()));
            }
            if (Files.notExists(imageStoragePath)) {
                Files.createDirectories(imageStoragePath);
                logger.info("Image storage directory created %s".formatted(fileStoragePath.toAbsolutePath().toString()));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Creation of storage directories failed", e);
        }
    }

    public String saveImage(MultipartFile file) {
        return saveFile(file, imageStorageLocation);
    }

    private String saveFile(MultipartFile file, String storageLocation) {
        Path filePath = createFilePath(file, storageLocation);
        if (Files.exists(filePath)) {
            return filePath.getFileName().toString();
        }
        try {
            Files.copy(file.getInputStream(), filePath);
            return filePath.getFileName().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path createFilePath(MultipartFile file, String storageLocation) {
        String originalFileName = file.getOriginalFilename();
        String fileBaseName = FilenameUtils.getBaseName(originalFileName);
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        String completeFilename = fileBaseName + "." + fileExtension;
        return Paths.get(storageLocation, completeFilename);
    }
}