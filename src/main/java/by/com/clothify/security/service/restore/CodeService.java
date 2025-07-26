package by.com.clothify.security.service.restore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeService {

    public String createCode(String email) {
        return "123456";
    }
}
