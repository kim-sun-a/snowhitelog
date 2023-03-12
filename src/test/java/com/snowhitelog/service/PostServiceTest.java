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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("글 여러개 조회")
    void test3() {
        // given
        postRepository.saveAll(List.of(
                Post.builder().title("foo1").content("bar1").build(),
                Post.builder().title("foo2").content("bar2").build()
        ));
        //when
        List<PostResponse> postList = postService.getList();

        //then
        assertEquals(2L, postList.size());

    }

}