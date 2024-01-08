package com.github.backend_1st_project.service.mapper;

import com.github.backend_1st_project.web.entity.PostEntity;
import com.github.backend_1st_project.web.dto.posts.PostsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "postId", source = "postId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "author", source = "user.email")
    @Mapping(target = "viewCount", source = "viewCount")
    @Mapping(target = "likeCount", source = "likeCount")
    @Mapping(target = "createdAt", source = "createdAt")
    PostsDTO entityToDTO(PostEntity postEntity);
}
