package com.futbol.TinBall_backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dgvj0kcez",
            "api_key", "993864933125814",
            "api_secret", "10IR-gSMqAgy4eTBzwjZKI30lU0"
        ));
    }
}