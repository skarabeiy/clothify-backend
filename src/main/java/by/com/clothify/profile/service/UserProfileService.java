package by.com.clothify.profile.service;

import by.com.clothify.profile.model.dto.UserProfileDto;
import by.com.clothify.profile.model.entity.UserProfile;
import by.com.clothify.profile.repository.UserProfileRepository;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import by.com.clothify.security.util.AuthUtils;
import by.com.clothify.security.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public UserProfileDto getUserProfile() {
        String email = AuthUtils.getEmail();

        try {
            UserProfile userProfile = userProfileRepository.findUserProfileByEmail(email).orElseThrow();
            return mapToUserProfileDto(userProfile);

        } catch (Exception e) {
            log.error("Failed get user profile information for: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void saveUserProfile(UserProfileDto userProfileDto) {
        String email = AuthUtils.getEmail();

        try {
            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isEmpty()) {
                log.error("User with email in token not found");
                throw new RuntimeException();
            }

            UserProfile userProfile = UserProfile.builder()
                    .user(optionalUser.get())
                    .name(userProfileDto.getName())
                    .gender(userProfileDto.getGender())
                    .dateOfBirth(DateUtils.covertToLocaleDate(userProfileDto.getDateOfBirth()))
                    .createdAt(LocalDateTime.now())
                    .build();

            userProfileRepository.save(userProfile);

        } catch (Exception e) {
            log.error("Failed fill information for user: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private UserProfileDto mapToUserProfileDto(UserProfile userProfile) {
        return UserProfileDto.builder()
                .name(userProfile.getName())
                .gender(userProfile.getGender())
                .dateOfBirth(DateUtils.convertToString(userProfile.getDateOfBirth()))
                .build();
    }
}
