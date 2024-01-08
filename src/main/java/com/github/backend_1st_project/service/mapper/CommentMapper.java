package com.github.backend_1st_project.service.mapper;

import com.github.backend_1st_project.web.dto.comments.CommentDTO;
import com.github.backend_1st_project.web.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper{
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "id", source = "comment_id")
    @Mapping(target = "postId", source = "post.postId")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "author", source = "user.email")
    CommentDTO entityToDTO(CommentEntity commentEntity);
}
