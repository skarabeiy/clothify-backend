package by.com.clothify.links.mapper;

import by.com.clothify.clothes.mapper.ClothesBrandMapper;
import by.com.clothify.clothes.mapper.ClothesCategoryMapper;
import by.com.clothify.links.model.dto.LinkDto;
import by.com.clothify.links.model.dto.SavedLinkDto;
import by.com.clothify.links.model.entity.Link;
import by.com.clothify.security.model.entity.User;

public class LinkMapper {

    public static LinkDto mapToLinkDto(Link link) {

        return LinkDto.builder()
                .id(link.getId())
                .name(link.getName())
                .url(link.getUrl())
                .category(
                        link.getCategory() == null
                        ? null
                        : ClothesCategoryMapper.mapToCategoryDto(link.getCategory())
                )
                .brand(
                        link.getBrand() == null
                        ? null
                        : ClothesBrandMapper.mapToClothesBrandDto(link.getBrand())
                )
                .color(link.getColor())
                .season(link.getSeason())
                .build();
    }

    public static Link mapToLink(SavedLinkDto savedLinkDto, Long userId) {
        return Link.builder()
                .name(savedLinkDto.getName())
                .url(savedLinkDto.getUrl())
                .user(
                        User.builder()
                                .id(userId)
                                .build()
                )
                .build();
    }
}
