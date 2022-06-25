//package com.itransition.coursework.attachment;
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * Abdulqodir Ganiev 6/18/2022 12:24 AM
// */
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class AttachmentService {
//
//    private final Cloudinary cloudinary;
//
//    public String uploadImage(MultipartFile file, String imgUrl) throws IOException {
//        if (file != null) {
//            File image = convert(file);
//            Map uploadResult = cloudinary.uploader().upload(image, ObjectUtils.emptyMap());
//            imgUrl = (String) uploadResult.get("url");
//            System.out.println(imgUrl);
//        }
//        return imgUrl;
//    }
//
//    public File convert(MultipartFile image) {
//        try {
//            File file = new File(Objects.requireNonNull(
//                    image.getOriginalFilename()
//            ));
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            fileOutputStream.write(image.getBytes());
//            fileOutputStream.close();
//            return file;
//        } catch (Exception e) {
//            log.info("cannot convert MultiPartFile to File {}", e.getMessage());
//            return null;
//        }
//    }
//}
