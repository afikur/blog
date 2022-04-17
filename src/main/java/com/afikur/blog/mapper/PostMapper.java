package com.afikur.blog.mapper;

import com.afikur.blog.dto.PostDto;
import com.afikur.blog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PostMapper {

    PostDto toDto(Post entity);

    Post toPost(PostDto dto);
}
