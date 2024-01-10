package com.github.backend_1st_project.web.controller;

import com.github.backend_1st_project.models.ResponseModel;
import com.github.backend_1st_project.repository.userDetails.CustomUserDetails;
import com.github.backend_1st_project.service.PostService;
import com.github.backend_1st_project.web.dto.ResultResponse;
import com.github.backend_1st_project.web.dto.posts.PostBody;
import com.github.backend_1st_project.web.dto.posts.PostsDTO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"게시판 API"})
public class PostsController {

    private final PostService postService;

    @ApiOperation(
            value = "게시판 정보 전체 조회",
            notes = "게시판 정보를 전체 조회"
    )
    @GetMapping("")
    public List<PostsDTO> findAllPosts() {
        List<PostsDTO> posts = postService.findAllPost();
        return posts;
    }

    @ApiOperation(
            value = "특정 사용자가 쓴 게시판 정보 조회",
            notes = "특정 사용자가 쓴 게시판 정보 조회")
    @ApiImplicitParam(
            name = "author_email",
            value = "사용자 이메일"
    )
    @GetMapping("/search")
    public List<PostsDTO> findPostsById(@RequestParam("author_email") String userEmail) {
        List<PostsDTO> posts = postService.findPostById(userEmail);
        return posts;
    }

    @ApiOperation(
      value = "게시판 글 추가",
      notes = "게시판 글을 추가 한다."
    )
    @PostMapping("")
    public ResponseModel registerPost(
            @RequestBody PostBody body,
            @ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String posts = postService.savePost(body, customUserDetails);
        return new ResponseModel(posts);
    }

    @ApiOperation(
      value = "게시판 글 수정",
      notes = "게시판 글을 수정한다."
    )
    @ApiImplicitParam(
      name = "postId",
      value = "게시판 번호"
    )
    @PutMapping("/{postId}")
    public ResponseModel updatePost(
            @PathVariable("postId") Integer postId,
            @RequestBody PostBody postBody
    ) {
        String result = postService.updatePosts(postId, postBody);
        return new ResponseModel(result);
    }

    @ApiOperation(
      value = "게시판 글 삭제",
      notes = "유저가 쓴 게시판을 삭제한다."
    )
    @ApiImplicitParam(
      name = "postId",
      value = "게시판 번호"
    )
    @DeleteMapping("/{postId}")
    public ResponseModel deleteItemByPathId(@PathVariable("postId") Integer postId) {
        String result = postService.deletePosts(postId);
        return new ResponseModel(result);
    }

    @ApiOperation(
      value = "게시판 글 좋아요",
      notes = "유저가 쓴 게시물을 좋아요한다."
    )
    @ApiImplicitParam(
      name = "postId",
      value = "게시판 번호"
    )
    @PostMapping("/likes/{postId}")
    public ResponseModel LikeItemByPathId(
            @PathVariable("postId") Integer postId,
            @ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String result = postService.likePosts(postId, customUserDetails);
        return new ResponseModel(result);
    }

    @ApiOperation(
      value = "게시판 글 좋아요 취소",
      notes = "좋아요한 게시물을 취소한다."
    )
    @ApiImplicitParam(
      name = "postId",
      value = "게시판 번호"
    )
    @DeleteMapping("/deleteLikes/{postId}")
    public ResponseModel DeleteLikeByPathId(
            @PathVariable("postId") Integer postId,
            @ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String result = postService.deleteLikePosts(postId, customUserDetails);
        return new ResponseModel(result);
    }

}
