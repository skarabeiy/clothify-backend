package by.com.clothify.demo.controller;

import by.com.clothify.demo.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DemoController {
    private final DemoService demoService;

    @GetMapping("/test")
    public String test() {
        return "Test Orashkov";
    }

    @GetMapping("/test-bd")
    public String testBD() {
        return demoService.testBD();
    }
}
