package by.com.clothify.links.service;

import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.links.mapper.LinkMapper;
import by.com.clothify.links.model.dto.LinkDto;
import by.com.clothify.links.model.dto.SavedLinkDto;
import by.com.clothify.links.model.entity.Link;
import by.com.clothify.links.repository.LinkRepository;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static by.com.clothify.links.mapper.LinkMapper.mapToLinkDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkService {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public ResponseEntity<LinkDto> getLinkById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Link> optionalLink = linkRepository.findByIdAndEmail(id, email);

            if (optionalLink.isEmpty()) {
                log.error("Can't find link by id = {} for user: {}", id, email);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(mapToLinkDto(optionalLink.get()));

        } catch (Exception e) {
            log.error("Failed get link by id = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public LinkDto saveLink(SavedLinkDto savedLinkDto) {

        final String email = AuthUtils.getEmail();

        try {
            User user = userRepository.findByEmail(email).orElseThrow();
            Link link = LinkMapper.mapToLink(savedLinkDto, user.getId());

            LocalDateTime now = LocalDateTime.now();
            link.setCreatedAt(now);
            link.setUpdatedAt(now);

            link = linkRepository.save(link);
            return mapToLinkDto(link);

        } catch (Exception e) {
            log.error("Failed save link: {} for user: {}", savedLinkDto.getName(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public ResponseEntity<Void> deleteLinkById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            linkRepository.deleteByIdAndUser_Email(id, email);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Failed delete link with ID = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
