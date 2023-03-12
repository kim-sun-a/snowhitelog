package com.snowhitelog.service;

import com.snowhitelog.domain.Post;
import com.snowhitelog.repository.PostRepository;
import com.snowhitelog.request.PostCreate;
import com.snowhitelog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        // postCreate -> Entity

        Post post = Post.builder().title(postCreate.getTitle()).content(postCreate.getContent()).build();
        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다"));
        /**
         * Controller -> WebPostService -> Repository
         *            -> PostService
         */

        return PostResponse.builder().id(post.getId()).title(post.getTitle()).content(post.getContent()).build();
    }

    public List<PostResponse> getList() {
        return postRepository.findAll().stream()
                .map(PostResponse::new
        ).collect(Collectors.toList());
    }
}
