package by.com.clothify.demo.service;

import by.com.clothify.demo.model.entity.Demo;
import by.com.clothify.demo.repository.DemoRepository;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DemoService {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    private final DemoRepository demoRepository;
    private final UserRepository userRepository;

    public String testBD() {
        log.info("Datasource url: {}", datasourceUrl);
        Demo demo = demoRepository.findAll().getFirst();
        return demo.getInfo();
    }

    public String testUsers() {
        User user = userRepository.findAll().getFirst();
        return user.getEmail();
    }
}
