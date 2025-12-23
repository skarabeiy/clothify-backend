package by.com.clothify.links.service;

import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.links.mapper.LinkMapper;
import by.com.clothify.links.model.dto.LinkDto;
import by.com.clothify.links.model.entity.Link;
import by.com.clothify.links.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkService {

    private final LinkRepository linkRepository;

    public ResponseEntity<LinkDto> getLinkById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            Optional<Link> optionalLink = linkRepository.findByIdAndEmail(id, email);

            if (optionalLink.isEmpty()) {
                log.error("Can't find link by id = {} for user: {}", id, email);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(LinkMapper.mapToLinkDto(optionalLink.get()));

        } catch (Exception e) {
            log.error("Failed get link by id = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
