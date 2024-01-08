package com.github.backend_1st_project.service;

import com.github.backend_1st_project.repository.comments.CommentsJpaRepository;
import com.github.backend_1st_project.repository.posts.PostsJpaRepository;
import com.github.backend_1st_project.repository.userDetails.CustomUserDetails;
import com.github.backend_1st_project.repository.users.UsersJpaRepository;
import com.github.backend_1st_project.service.exception.NotFoundException;
import com.github.backend_1st_project.service.mapper.CommentMapper;
import com.github.backend_1st_project.web.dto.comments.CommentBody;
import com.github.backend_1st_project.web.dto.comments.CommentDTO;
import com.github.backend_1st_project.web.entity.CommentEntity;
import com.github.backend_1st_project.web.entity.PostEntity;
import com.github.backend_1st_project.web.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsJpaRepository commentsJpaRepository;
    private final UsersJpaRepository usersJpaRepository;
    private final PostsJpaRepository postsJpaRepository;
    public List<CommentDTO> findPostByComments(Integer postId) {
        List<CommentEntity> comments = commentsJpaRepository.findByPostId(postId);
        if(comments.isEmpty())
            throw new NotFoundException("존재하지 않는 댓글입니다.");
        List<CommentDTO> dto = comments.stream().map(CommentMapper.INSTANCE::entityToDTO).collect(Collectors.toList());
        return dto;
    }

    @Transactional
    public String saveComment(CommentBody body, CustomUserDetails customUserDetails){
        if(customUserDetails.equals(body.getAuthor()))
            throw new NotFoundException("유저가 다릅니다.");
        UserEntity user = usersJpaRepository.findByEmailEquals(customUserDetails.getEmail());
        PostEntity post = postsJpaRepository.findByPostId(body.getPostId());
        if(user == null)
            throw new NotFoundException("존재하지 않은 유저입니다.");
        else if(post == null)
            throw new NotFoundException("존재하지 않은 게시물입니다.");
        CommentEntity comments = new CommentEntity(body.getContent(), post, user, LocalDateTime.now(), LocalDateTime.now());
        commentsJpaRepository.save(comments);
        return "댓글이 성공적으로 작성되었습니다.";
    }

    @Transactional
    public String updateComment(Integer commentId, CommentBody body) {
        CommentEntity comments = commentsJpaRepository.findById(commentId).orElseThrow(() -> new NotFoundException("해당 댓글이 존재하지 않습니다."));
        comments.setCommentBody(body);
        return "댓글이 성공적으로 수정되었습니다.";
    }

    @Transactional
    public String deleteComment(Integer commentId) {
        commentsJpaRepository.deleteById(commentId);
        return "댓글이 성공적으로 삭제되었습니다.";
    }
}
