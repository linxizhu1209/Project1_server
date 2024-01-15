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

import com.github.backend_1st_project.models.ResponseModel;
import com.github.backend_1st_project.repository.userDetails.CustomUserDetails;
import com.github.backend_1st_project.service.PostService;
import com.github.backend_1st_project.web.dto.posts.PostBody;
import com.github.backend_1st_project.web.dto.posts.PostsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
@Tag(name="Post",description = "Post API Document")
public class PostsController {

    private final PostService postService;

    @Operation(summary = "게시물 전체 조회",description = "전체 게시물을 조회" )
    @GetMapping("")
    public List<PostsDTO> findAllPosts() {
        List<PostsDTO> posts = postService.findAllPost();
        return posts;
    }

    @Operation(summary = "게시물 조회",description = "특정 사용자가 쓴 게시물 조회" )
    @GetMapping("/search")
    public List<PostsDTO> findPostsById(@Parameter(description = "사용자 이메일") String userEmail) {
        List<PostsDTO> posts = postService.findPostById(userEmail);
        return posts;
    }


    @Operation(summary = "게시물 추가",description = "게시판에 글 추가" )
    @PostMapping("")
    public ResponseModel registerPost(
            @RequestBody PostBody body,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String posts = postService.savePost(body, customUserDetails);
        return new ResponseModel(posts);
    }



    @Operation(summary = "특정 게시물을 수정",description = "특정 게시물을 수정")
    @PutMapping("/{postId}")
    public ResponseModel updatePost(
            @Parameter(description = "게시판 번호") Integer postId,
            @RequestBody PostBody postBody
    ) {
        String result = postService.updatePosts(postId, postBody);
        return new ResponseModel(result);
    }


    @Operation(summary = "게시물 삭제",description = "특정 게시물 삭제",tags = {"delete"} )
    @DeleteMapping("/{postId}")
    public ResponseModel deleteItemByPathId(@Parameter(description = "게시판 번호") Integer postId) {
        String result = postService.deletePosts(postId);
        return new ResponseModel(result);
    }

    @Operation(summary = "게시물 좋아요",description = "특정 게시물을 좋아요")
    @PostMapping("/likes/{postId}")
    public ResponseModel LikeItemByPathId(
            @Parameter(description = "게시물 번호") Integer postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String result = postService.likePosts(postId, customUserDetails);
        return new ResponseModel(result);
    }

    @Operation(summary = "게시물 좋아요",description = "특정 게시물을 좋아요")
    @DeleteMapping("/deleteLikes/{postId}")
    public ResponseModel DeleteLikeByPathId(
            @Parameter(description = "게시물 번호") Integer postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String result = postService.deleteLikePosts(postId, customUserDetails);
        return new ResponseModel(result);
    }

}