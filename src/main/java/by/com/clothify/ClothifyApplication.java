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
// TODO- подумать над кешированием
// TODO: пересмотреть индексы
// TODO- при выносе на прод подумать про логи, метрики
// TODO - перешел на спринг бут 4 - если будет тяжело вернусь обратно на 3
public class ClothifyApplication {
    static void main(String[] args) {
        SpringApplication.run(ClothifyApplication.class, args);
    }
}
