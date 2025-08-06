package by.com.clothify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClothifyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClothifyApplication.class, args);
    }
}
