package com.snowhitelog.service;

import com.snowhitelog.domain.Post;
import com.snowhitelog.exception.PostNotPound;
import com.snowhitelog.repository.PostRepository;
import com.snowhitelog.request.PostCreate;
import com.snowhitelog.request.PostEdit;
import com.snowhitelog.request.PostSearch;
import com.snowhitelog.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        assertEquals("1234567890", post.getTitle());
        assertEquals("bar", post.getContent());

    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {
        // given
        List<Post> requestPosts = IntStream.range(1,31)
                        .mapToObj(i ->  Post.builder()
                                    .title("foo " + i)
                                    .content("bar  " + i)
                                    .build())
                        .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder().page(1).build();
        // sql -> select, limit, offset
        //when
        List<PostResponse> postList = postService.getList(postSearch);

        //then
        assertEquals(10L, postList.size());
        assertEquals("foo 30", postList.get(0).getTitle());
    }

    @Test
    @DisplayName("게시글 제목 수정")
    void test4() {
        // given
        Post post = Post.builder()
                .title("서나쓰")
                .content("반포자이")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("서나체고")
                .content(null)
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changePost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        assertEquals("서나체고", changePost.getTitle());
        assertEquals("반포자이", changePost.getContent());
    }

    @Test
    @DisplayName("게시글 내용 수정")
    void test5() {
        // given
        Post post = Post.builder()
                .title("서나쓰")
                .content("반포자이")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("서나쓰")
                .content("초가집")
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changePost = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));
        assertEquals("초가집", changePost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        // given
        Post post = Post.builder()
                .title("서나쓰")
                .content("반포자이")
                .build();
        postRepository.save(post);

        //when
        postService.delete(post.getId());

        //then
        assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test7() {
        // given
        Post post = Post.builder().title("백설서나").content("반포자이").build();
        postRepository.save(post);

        //expected
        assertThrows(PostNotPound.class, () -> {
            postService.get(post.getId() + 1);
        });
    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test8() {
        // given
        Post post = Post.builder()
                .title("서나쓰")
                .content("반포자이")
                .build();
        postRepository.save(post);

        //expected
        assertThrows(PostNotPound.class, () -> {
            postService.delete(post.getId()+1);
        });
    }

    @Test
    @DisplayName("게시글 내용 수정 - 존재하지 않는 글")
    void test9() {
        // given
        Post post = Post.builder()
                .title("서나쓰")
                .content("반포자이")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("서나쓰")
                .content("초가집")
                .build();

        //expected
        assertThrows(PostNotPound.class, () -> {
            postService.edit(post.getId()+1, postEdit);
        });
    }
}