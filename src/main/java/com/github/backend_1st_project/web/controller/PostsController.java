package com.github.backend_1st_project.web.controller;

import com.github.backend_1st_project.models.ResponseModel;
import com.github.backend_1st_project.repository.userDetails.CustomUserDetails;
import com.github.backend_1st_project.service.PostService;
import com.github.backend_1st_project.web.dto.ResultResponse;
import com.github.backend_1st_project.web.dto.posts.PostBody;
import com.github.backend_1st_project.web.dto.posts.PostsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"게시판 API"})
public class PostsController {

    private final PostService postService;

    @ApiOperation(value="게시판 정보 전체 조회", notes="게시판 정보를 전체조회")
    @GetMapping("")
    public List<PostsDTO> findAllPosts(){
        List<PostsDTO> posts = postService.findAllPost();
        return posts;
    }

    @ApiOperation(value="유저가 쓴 게시판 정보 조회", notes="유저가 쓴 게시판 정보조회")
    @GetMapping("/search")
    public List<PostsDTO> findPostsById(@RequestParam("author_email") String userEmail){
        List<PostsDTO> posts = postService.findPostById(userEmail);
        return posts;
    }

    @ApiOperation(value="게시판 글 추가", notes="게시판 글을 추가한다.")
    @PostMapping("")
    public ResponseModel registerPost(
            @RequestBody PostBody body,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        String posts = postService.savePost(body, customUserDetails);
        return new ResponseModel(posts);
    }

    @ApiOperation(value="게시판 글 수정", notes="게시판 글을 수정한다.")
    @PutMapping("/{postId}")
    public ResponseModel updatePost(@PathVariable("postId") Integer postId, @RequestBody PostBody postBody){
        String result = postService.updatePosts(postId, postBody);
        return new ResponseModel(result);
    }

    @ApiOperation(value="게시판 글 삭제", notes="유저가 쓴 게시판을 삭제한다.")
    @DeleteMapping("/{postId}")
    public ResponseModel deleteItemByPathId(@PathVariable("postId") Integer postId){
        String result = postService.deletePosts(postId);
        return new ResponseModel(result);
    }

}
