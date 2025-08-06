package by.com.clothify.account.service;

import by.com.clothify.security.model.dto.UserInformationDto;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import by.com.clothify.security.util.AuthUtils;
import by.com.clothify.security.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void fillInformation(UserInformationDto userInformationDto) {
        String email = AuthUtils.getEmail();
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow();

//            user.setName(userInformationDto.getName());
//            user.setGender(userInformationDto.getGender());
//            user.setDateOfBirth(DateUtils.covertToLocaleDate(userInformationDto.getDateOfBirth()));

            userRepository.save(user);
        } catch (Exception e) {
            log.error("Failed fill information for user: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
