package by.com.clothify.profile.service;

import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.common.util.DateUtils;
import by.com.clothify.profile.model.dto.UserProfilePatchRequestDto;
import by.com.clothify.profile.model.dto.UserProfileRequestDto;
import by.com.clothify.profile.model.dto.UserProfileResponseDto;
import by.com.clothify.profile.model.entity.UserProfile;
import by.com.clothify.profile.repository.UserProfileRepository;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static by.com.clothify.common.util.DateUtils.covertToLocaleDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public UserProfileResponseDto getUserProfile() {
        String email = AuthUtils.getEmail();

        try {
            UserProfile userProfile = userProfileRepository.findByUserAndEmail(email).orElseThrow();
            return mapToUserProfileDto(userProfile);

        } catch (Exception e) {
            log.error("Failed get user profile information for: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void saveUserProfile(UserProfileRequestDto userProfileRequestDto) {
        String email = AuthUtils.getEmail();

        try {
            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isEmpty()) {
                logUserNotFound(email);
                throw new RuntimeException();
            }

            UserProfile userProfile = UserProfile.builder()
                    .user(optionalUser.get())
                    .name(userProfileRequestDto.getName())
                    .gender(userProfileRequestDto.getGender())
                    .dateOfBirth(covertToLocaleDate(userProfileRequestDto.getDateOfBirth()))
                    .createdAt(LocalDateTime.now())
                    .build();

            userProfileRepository.save(userProfile);

        } catch (Exception e) {
            log.error("Failed save user profile information for: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void patchUserProfile(UserProfilePatchRequestDto request) {
        String email = AuthUtils.getEmail();

        try {
            Optional<UserProfile> optionalUserProfile = userProfileRepository.findByUserAndEmail(email);

            if (optionalUserProfile.isEmpty()) {
                logUserNotFound(email);
                throw new RuntimeException();
            }

            UserProfile userProfile = optionalUserProfile.get();
            setUserProfileFields(userProfile, request);
            userProfileRepository.save(userProfile);

        } catch (Exception e) {
            log.error("Failed patch user profile information for: {}", email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void setUserProfileFields(UserProfile userProfile, UserProfilePatchRequestDto request) {
        if (request.getName() != null) {
            userProfile.setName(request.getName());
        }

        if (request.getGender() != null) {
            userProfile.setGender(request.getGender());
        }

        if (request.getDateOfBirth() != null) {
            userProfile.setDateOfBirth(covertToLocaleDate(request.getDateOfBirth()));
        }
    }

    private UserProfileResponseDto mapToUserProfileDto(UserProfile userProfile) {
        return UserProfileResponseDto.builder()
                .email(userProfile.getUser().getEmail())
                .name(userProfile.getName())
                .gender(userProfile.getGender())
                .dateOfBirth(DateUtils.convertToString(userProfile.getDateOfBirth()))
                .build();
    }

    private void logUserNotFound(String email) {
        log.error("User with email: {} in token not found", email);
    }
}
