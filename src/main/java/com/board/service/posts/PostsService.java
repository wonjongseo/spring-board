package com.board.service.posts;

import com.board.domain.posts.Posts;
import com.board.domain.posts.PostsRepository;
import com.board.web.dto.PostSaveRequestDto;
import com.board.web.dto.PostsResponseDto;
import com.board.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostSaveRequestDto postsResponseDto) {
        return postsRepository.save(postsResponseDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {

        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("NOT FOUND POST . id = " + id));

        posts.update(requestDto.getTitle() , requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("NOT FOUND POST . id = " + id));

        return new PostsResponseDto(posts);
    }
}
