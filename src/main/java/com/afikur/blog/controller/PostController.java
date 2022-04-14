package com.afikur.blog.controller;

import com.afikur.blog.dto.PostDto;
import com.afikur.blog.mapper.PostMapper;
import com.afikur.blog.model.Post;
import com.afikur.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    private PostMapper postMapper;

    @Autowired
    public void setPostMapper(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @GetMapping
    public String getPosts(Model model) {
        List<PostDto> posts = postService
                .findAll()
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());

        model.addAttribute("posts", posts);
        return "post/postList";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("post", new PostDto());
        return "post/postForm";
    }

    @PostMapping("/add")
    public String savePost(@ModelAttribute @Valid PostDto postDto) {
        Post post = postMapper.toPost(postDto);
        postService.save(post);
        return "redirect:/posts";
    }
}
