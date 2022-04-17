package com.afikur.blog.controller;

import com.afikur.blog.dto.PostDto;
import com.afikur.blog.mapper.PostMapper;
import com.afikur.blog.model.Post;
import com.afikur.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/posts")
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("/{id}")
    public String getPostById(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post Id: " + id));

        PostDto postDto = postMapper.toDto(post);

        model.addAttribute("post", postDto);
        return "post/singlePost";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("post", new PostDto());
        return "post/postForm";
    }

    @PostMapping("/add")
    public String savePost(@ModelAttribute("post") @Valid PostDto postDto, BindingResult result) {
        if (result.hasErrors()) {
            return "post/postForm";
        }

        Post post = postMapper.toPost(postDto);
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post Id: " + id));
        log.debug("Post: {}", post);
        PostDto postDto = postMapper.toDto(post);
        log.debug("PostDto: {}", postDto);
        model.addAttribute("post", postDto);

        return "post/postEditForm";
    }

    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute("post") @Valid PostDto postDto, BindingResult result) {
        if(result.hasErrors()) {
            return "post/postEditForm";
        }
        log.info("createdAt: {}", postDto.getCreatedAt());
        Post post = postMapper.toPost(postDto);
        postService.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        postService.delete(post);
        return "redirect:/posts";
    }
}
