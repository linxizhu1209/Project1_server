package com.github.backend_1st_project.service.mapper;

import com.github.backend_1st_project.web.dto.comments.CommentDTO;
import com.github.backend_1st_project.web.entity.CommentEntity;
import com.github.backend_1st_project.web.entity.PostEntity;
import com.github.backend_1st_project.web.entity.UserEntity;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-07T11:16:59+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDTO entityToDTO(CommentEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( commentEntity.getComment_id() );
        commentDTO.setPostId( commentEntityPostPostId( commentEntity ) );
        commentDTO.setContent( commentEntity.getContent() );
        commentDTO.setAuthor( commentEntityUserEmail( commentEntity ) );
        if ( commentEntity.getCreatedAt() != null ) {
            commentDTO.setCreatedAt( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( commentEntity.getCreatedAt() ) );
        }

        return commentDTO;
    }

    private Integer commentEntityPostPostId(CommentEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }
        PostEntity post = commentEntity.getPost();
        if ( post == null ) {
            return null;
        }
        Integer postId = post.getPostId();
        if ( postId == null ) {
            return null;
        }
        return postId;
    }

    private String commentEntityUserEmail(CommentEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }
        UserEntity user = commentEntity.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
