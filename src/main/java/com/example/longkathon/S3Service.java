package com.example.longkathon;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("파일 변환에 실패했습니다."));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        // Public Read 권한으로 S3에 업로드
        amazonS3Client.putObject(bucket, fileName, uploadFile);
        // S3에 업로드된 파일의 URL 반환
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("임시 파일이 삭제되었습니다.");
        } else {
            log.warn("임시 파일 삭제에 실패했습니다.");
        }
    }

    public Optional<File> convert(MultipartFile file) throws IOException {
        // 기존 파일 이름으로 새 파일 생성 (프로그램 루트 디렉토리에 생성됨)
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 파일이 없을 경우 새 파일 생성
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes()); // MultipartFile의 내용을 새 파일에 작성
            }
            return Optional.of(convertFile);
        }
        // 파일 생성 실패 시 빈 Optional 반환
        return Optional.empty();
    }
}
