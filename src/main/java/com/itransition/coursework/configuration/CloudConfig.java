package com.itransition.coursework.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Abdulqodir Ganiev 6/18/2022 12:15 AM
 */

@Configuration
public class CloudConfig {

    @Value("${cloudinary.name}")
    String cloudName;

    @Value("${cloudinary.apiKey}")
    String apiKey;

    @Value("${cloudinary.apiSecret}")
    String apiSecret;

    @Bean
    public Cloudinary cloudinaryConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }
}
