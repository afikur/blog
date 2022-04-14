package com.afikur.blog.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class PostDto {
    @NotBlank(message = "Title can't be empty.")
    @Size(min = 10, message = "Title must be at least 10 characters.")
    private String title;

    @NotBlank(message = "Body can't be empty")
    @Size(min = 20, message = "Body must be at least 30 characters.")
    private String body;
}
