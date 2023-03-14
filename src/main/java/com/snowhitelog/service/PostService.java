package com.snowhitelog.service;

import com.snowhitelog.domain.Post;
import com.snowhitelog.repository.PostRepository;
import com.snowhitelog.request.PostCreate;
import com.snowhitelog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // 글이 너무 많은 경우 -> 비용이 많이 든다
    // 글이 1억개 잇는 경우 db에서 모두 조회하면 db가 뻗을 수 잇다
    // db -> 애플리케이션 서버로 전달하는 시간, 트래픽등이 많이 발생할 수 있다
    public List<PostResponse> getList(Pageable page) {
        // web -> page 1 -> 0
        return postRepository.findAll(page).stream()
                .map(PostResponse::new
        ).collect(Collectors.toList());
    }
}
