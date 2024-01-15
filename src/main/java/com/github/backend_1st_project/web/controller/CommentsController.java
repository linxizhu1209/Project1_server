package com.github.backend_1st_project.web.controller;


import com.github.backend_1st_project.models.ResponseModel;
import com.github.backend_1st_project.repository.userDetails.CustomUserDetails;
import com.github.backend_1st_project.service.CommentService;
import com.github.backend_1st_project.web.dto.ResultResponse;
import com.github.backend_1st_project.web.dto.comments.CommentBody;
import com.github.backend_1st_project.web.dto.comments.CommentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Tag(name="Comments",description = "Comments API Document")
public class CommentsController {
    private final CommentService commentService;

    @Operation(summary = "댓글 조회",description = "특정 게시물의 댓글 조회")
    @GetMapping("/{postId}/comments")
    public ResultResponse findByComment(@Parameter(description = "게시물 번호") Integer postId){
        List<CommentDTO> comments = commentService.findPostByComments(postId);
        return new ResultResponse(comments);
    }

    @Operation(summary = "댓글 남기기",description = "특정 게시물에 댓글 추가")
    @PostMapping("/comments")
    public ResponseModel registerComment(
            @RequestBody CommentBody body,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        String result = commentService.saveComment(body, customUserDetails);
        return new ResponseModel(result);
    }

    @Operation(summary = "댓글 수정",description = "특정 게시물의 댓글 수정")
    @PutMapping("/comments/{commentId}")
    public ResponseModel updateComment(@Parameter(description = "댓글 번호") Integer commentId, @RequestBody CommentBody body){
        String result = commentService.updateComment(commentId, body);
        return new ResponseModel(result);
    }

    @Operation(summary = "댓글 삭제",description = "특정 게시물의 댓글 삭제",tags = {"delete"})
    @DeleteMapping("/comments/{commentId}")
    public ResponseModel updateComment(@Parameter(description = "댓글 번호") Integer commentId){
        String result = commentService.deleteComment(commentId);
        return new ResponseModel(result);
    }
}
