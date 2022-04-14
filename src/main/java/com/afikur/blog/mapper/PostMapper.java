package com.afikur.blog.mapper;

import com.afikur.blog.dto.PostDto;
import com.afikur.blog.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto postToPostDto(Post entity);

    Post postDtoToPost(PostDto dto);
}
