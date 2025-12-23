package by.com.clothify.links.mapper;

import by.com.clothify.clothes.mapper.ClothesBrandMapper;
import by.com.clothify.clothes.mapper.ClothesCategoryMapper;
import by.com.clothify.links.model.dto.LinkDto;
import by.com.clothify.links.model.entity.Link;

public class LinkMapper {

    public static LinkDto mapToLinkDto(Link link) {

        return LinkDto.builder()
                .id(link.getId())
                .name(link.getName())
                .url(link.getUrl())
                .category(ClothesCategoryMapper.mapToCategoryDto(link.getCategory()))
                .brand(ClothesBrandMapper.mapToClothesBrandDto(link.getBrand()))
                .color(link.getColor())
                .season(link.getSeason())
                .build();
    }
}
