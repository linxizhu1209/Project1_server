package com.github.backend_1st_project.service.mapper;

import com.github.backend_1st_project.web.dto.posts.PostsDTO;
import com.github.backend_1st_project.web.entity.PostEntity;
import com.github.backend_1st_project.web.entity.UserEntity;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-07T11:03:49+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
public class PostMapperImpl implements PostMapper {

    @Override
    public PostsDTO entityToDTO(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }

        PostsDTO postsDTO = new PostsDTO();

        postsDTO.setPostId( postEntity.getPostId() );
        postsDTO.setTitle( postEntity.getTitle() );
        postsDTO.setAuthor( postEntityUserEmail( postEntity ) );
        postsDTO.setViewCount( postEntity.getViewCount() );
        postsDTO.setLikeCount( postEntity.getLikeCount() );
        if ( postEntity.getCreatedAt() != null ) {
            postsDTO.setCreatedAt( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( postEntity.getCreatedAt() ) );
        }
        postsDTO.setContent( postEntity.getContent() );

        return postsDTO;
    }

    private String postEntityUserEmail(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }
        UserEntity user = postEntity.getUser();
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
