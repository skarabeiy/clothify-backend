package by.com.clothify.demo.service;

import by.com.clothify.demo.model.entity.Demo;
import by.com.clothify.demo.repository.DemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoService {
    private final DemoRepository demoRepository;

    public String testBD() {
        Demo demo = demoRepository.findAll().getFirst();
        return demo.getInfo();
    }
}
