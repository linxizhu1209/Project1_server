package com.github.backend_1st_project.service;

import com.github.backend_1st_project.repository.Likes.LikesJpaRepository;
import com.github.backend_1st_project.repository.userDetails.CustomUserDetails;
import com.github.backend_1st_project.repository.users.UsersJpaRepository;
import com.github.backend_1st_project.service.exception.NotFoundException;
import com.github.backend_1st_project.web.entity.LikesEntity;
import com.github.backend_1st_project.web.entity.PostEntity;
import com.github.backend_1st_project.repository.posts.PostsJpaRepository;
import com.github.backend_1st_project.service.mapper.PostMapper;
import com.github.backend_1st_project.web.entity.UserEntity;
import com.github.backend_1st_project.web.dto.posts.PostBody;
import com.github.backend_1st_project.web.dto.posts.PostsDTO;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostsJpaRepository postsJpaRepository;
    private final UsersJpaRepository usersJpaRepository;
    private final LikesJpaRepository likesJpaRepository;

    public List<PostsDTO> findAllPost() {
        List<PostEntity> posts = postsJpaRepository.findAll();
        List<PostsDTO> postDto = posts.stream().map(PostMapper.INSTANCE::entityToDTO).collect(Collectors.toList());
        return postDto;
    }

    @Transactional
    public String savePost(PostBody body, CustomUserDetails customUserDetails) {

        UserEntity user = usersJpaRepository.findByEmailEquals(customUserDetails.getEmail());

        if(user == null)
            throw new NotFoundException("존재하지 않은 유저입니다.");
        PostEntity posts = new PostEntity(body.getTitle(), body.getContent(), user, LocalDateTime.now(), LocalDateTime.now());
        postsJpaRepository.save(posts);
        return "게시물이 성공적으로 작성되었습니다.";
    }

    @Transactional
    public String updatePosts(Integer postId, PostBody postBody) {
        PostEntity postEntity = postsJpaRepository.findById(postId).orElseThrow(() -> new NotFoundException("해당 게시물이 존재하지 않습니다."));
        UserEntity user = usersJpaRepository.findByEmailEquals(postBody.getAuthor());
        if(user == null)
          throw new NotFoundException("작성자 변경은 불가입니다.");
        postEntity.setPostBody(postBody);
        return "게시물 업데이트가 완료되었습니다.";
    }

    @Transactional
    public String deletePosts(Integer postId) {
        postsJpaRepository.findById(postId).orElseThrow(() -> new NotFoundException("해당 게시물은 이미 존재하지 않습니다."));
        postsJpaRepository.deleteById(postId);
        return "해당 게시물이 삭제되었습니다.";
    }

    public List<PostsDTO> findPostById(String userEmail) {
        List<PostEntity> posts = postsJpaRepository.findByUserEmail(userEmail);
        if(posts.isEmpty())
            throw new NotFoundException("해당 Email: " + userEmail + "의 게시물을 찾을 수 없습니다.");
        List<PostsDTO> dto = posts.stream().map(PostMapper.INSTANCE::entityToDTO).collect(Collectors.toList());
        return dto;
    }

    @Transactional
    public String likePosts(Integer postId, CustomUserDetails customUserDetails) {
        PostEntity post = postsJpaRepository.findByPostId(postId);
        if(post == null)
          throw new NotFoundException("삭제된 게시물입니다.");
        String userEmail = customUserDetails.getEmail();
        UserEntity user = usersJpaRepository.findByEmailEquals(userEmail);
        String returnStr = "";
        if(likesJpaRepository.existsByUserEqualsAndPostEquals(user,post)){
            returnStr = "이미 좋아요한 게시물입니다.";
        }
        else{
            LikesEntity likes = LikesEntity.builder().user(user).post(post).createdAt(LocalDateTime.now()).build();
            likesJpaRepository.save(likes);
            post.setLikeCount(1);
            postsJpaRepository.save(post);
            returnStr = "해당 게시물을 좋아요 했습니다";
        }
        return returnStr;
    }

    @Transactional
    public String deleteLikePosts(Integer postId,CustomUserDetails customUserDetails) {
        String returnstr = "";
        PostEntity post = postsJpaRepository.findByPostId(postId);
        if(post == null)
          throw new NotFoundException("삭제된 게시물입니다.");
        UserEntity user = usersJpaRepository.findByEmailEquals(customUserDetails.getEmail());
        List<UserEntity> users = post.getLikesList().stream().map(LikesEntity::getUser).toList();
        boolean alreadyLike = users.contains(user);
        if(alreadyLike){
            LikesEntity likes = likesJpaRepository.findLikesEntityByPostEqualsAndUserEquals(post,user);
            likesJpaRepository.delete(likes);
            post.decreaseLikeCount();
            postsJpaRepository.save(post);
            returnstr = "좋아요가 취소되었습니다";
        }
        else{
            returnstr = "취소할 좋아요가 없습니다.";
        }
        return returnstr;
    }
}
