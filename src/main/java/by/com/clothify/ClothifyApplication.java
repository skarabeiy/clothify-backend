package by.com.clothify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableCaching
// TODO подумать над кешированием
// TODO пересмотреть индексы
// TODO пересмотреть дебагом запросы
public class ClothifyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClothifyApplication.class, args);
    }
}
