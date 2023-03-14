package com.snowhitelog.service;

import com.snowhitelog.domain.Post;
import com.snowhitelog.repository.PostRepository;
import com.snowhitelog.request.PostCreate;
import com.snowhitelog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        //given
        PostCreate postCreate = PostCreate.builder().title("제목입니다").content("내용입니다").build();

        //when
        postService.write(postCreate);

        //then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다", post.getTitle());
        assertEquals("내용입니다", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        Post reqeustPost = Post.builder().title("1234567890123456").content("bar").build();
        postRepository.save(reqeustPost);

        //when
        PostResponse post = postService.get(reqeustPost.getId());

        //then
        assertNotNull(post);
        assertEquals(1L, postRepository.count());
        assertEquals("1234567890123456", post.getTitle());
        assertEquals("bar", post.getContent());

    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {
        // given
        List<Post> requestPosts = IntStream.range(1,31)
                        .mapToObj(i ->  Post.builder()
                                    .title("서나맨 제목 " + i)
                                    .content("반포자이  " + i)
                                    .build())
                        .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        Pageable pageable = PageRequest.of(0, 5, Sort.by(DESC, "id"));
        // sql -> select, limit, offset
        //when
        List<PostResponse> postList = postService.getList(pageable);

        //then
        assertEquals(5L, postList.size());
        assertEquals("서나맨 제목 30", postList.get(0).getTitle());
        assertEquals("서나맨 제목 26", postList.get(4).getTitle());
    }

}