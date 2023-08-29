package com.snowhitelog.controller;

import com.snowhitelog.request.PostCreate;
import com.snowhitelog.request.PostEdit;
import com.snowhitelog.request.PostSearch;
import com.snowhitelog.response.PostResponse;
import com.snowhitelog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    // SSR -> jsp, thymeleaf, mustache, freemarker
            //- html rendering
    // SPA ->
    // vue -> vue+SSR = nuxt.js  -? javascript <-> API JSON
    // react -> react+SSR = next

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        // 데이터 검증 이유
        // 1. client 개발자가 깜빡할 수 있다 실수로 값을 안보낼 수 있다
        // 2. client bug로 인해 값이 누락될 수 있다
        // 3. 외부의 나쁜 사람이 값을 임의로 조작해서 보낼 수 있다
        // 4. DB에 값을 저장할 때 의도치않은 오류가 발생할 수 있다
        // 5. 서버 개발자의 편안함을 위해서

        // 1. 매번 메서드마다 값을 검증해야한다
        //      > 개발자가 까먹을 수 있다
        //      > 검증 부분에서 버그가 발생할 여지가 높다
        //2. 응답값에 HashMap -> 응답 클래스를 만들어 주는게 좋다
        //3. 여러 개의 에러처리 힘듬
        //4. 세 번 이상의 반복적인 작업은 피한다 -> 자동화 고려


//        String title = params.getTitle();
//        if(title == null || title.equals("")) {
//            // 1. 항목이 많을 경우 빡세다
//            // 2. 개발 팁 -> 무언가 3번 이상 반복작업을 할 때 잘못한 건 아닌지 의심한다
//            // 3. 누락될 가능성이 있다
//            // 4. 생각보다 검증해야 할 것이 많다(꼼꼼하지 않을 수 있다)
//            // 5. 뭔가 개발자스럽지 않다 -> 간지X
//            throw new Exception("타이틀값이 없어요");
//        }

        //case 1. 저장한 데이터 entity -> response로 응답하기
        //case 2. 저장한 데이터 primary_id -> response로 응답하기
        //      client에서는 수신한 id를 글 조회 api를 통해서 데이터 수신받음
        //case 3. 응닶 필요 없음 -> 클라이언트에서 모든 글 데이터 context를 잘 관리함
        //bad case: 서버에서 반드시 이렇게 할겁니다 fix
        //          -> 서버에서는 유연하게 대응하는 것이 좋음
        //          -> 한번에 일괄적으로 잘 처리되는 케이스가 없다 -> 잘 관리하는 형태가 중요

        request.validate();
        postService.write(request);
    }

    /**
     *  /posts -> 글 전체 조회 (검색+페이징)
     *  /posts/{postId} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        // Request(DTO) Class
        // Response(DTO) Class

        //응답 클래스를 분리
        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit) {
        postService.edit(postId, postEdit);
    }

    @DeleteMapping("/post/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }

}
