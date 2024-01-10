package com.github.backend_1st_project.web.controller;

import com.github.backend_1st_project.models.ResponseModel;
import com.github.backend_1st_project.repository.userDetails.CustomUserDetails;
import com.github.backend_1st_project.service.CommentService;
import com.github.backend_1st_project.web.dto.ResultResponse;
import com.github.backend_1st_project.web.dto.comments.CommentBody;
import com.github.backend_1st_project.web.dto.comments.CommentDTO;
import com.github.backend_1st_project.web.dto.posts.PostBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"게시판 댓글 API"})
public class CommentsController {
    private final CommentService commentService;

    @ApiOperation(
      value="해당 게시물의 댓글 조회",
      notes="게시판 정보를 전체조회"
    )
    @ApiImplicitParam(
      name = "postId",
      value = "게시판 번호"
    )
    @GetMapping("/{postId}/comments")
    public ResultResponse findByComment(@PathVariable("postId") Integer postId){
        List<CommentDTO> comments = commentService.findPostByComments(postId);
        return new ResultResponse(comments);
    }

    @ApiOperation(
      value="댓글 추가",
      notes="게시판의 댓글을 추가한다."
    )
    @PostMapping("/comments")
    public ResponseModel registerComment(
            @RequestBody CommentBody body,
            @ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        String result = commentService.saveComment(body, customUserDetails);
        return new ResponseModel(result);
    }

    @ApiOperation(
      value="댓글 수정",
      notes="게시판의 댓글을 수정한다."
    )
    @ApiImplicitParam(
      name = "commentId",
      value = "댓글 번호"
    )
    @PutMapping("/comments/{commentId}")
    public ResponseModel updateComment(@PathVariable("commentId") Integer commentId, @RequestBody CommentBody body){
        String result = commentService.updateComment(commentId, body);
        return new ResponseModel(result);
    }

    @ApiOperation(
      value="댓글 삭제",
      notes="게시판의 댓글을 삭제한다."
    )
    @ApiImplicitParam(
      name = "commentId",
      value = "댓글 번호"
    )
    @DeleteMapping("/comments/{commentId}")
    public ResponseModel updateComment(@PathVariable("commentId") Integer commentId){
        String result = commentService.deleteComment(commentId);
        return new ResponseModel(result);
    }
}
