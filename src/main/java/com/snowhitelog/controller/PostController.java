package com.snowhitelog.controller;

import com.snowhitelog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {
    // SSR -> jsp, thymeleaf, mustache, freemarker
            //- html rendering
    // SPA ->
    // vue -> vue+SSR = nuxt.js  -? javascript <-> API JSON
    // react -> react+SSR = next

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) {
        // 데이터 검증 이유
        // 1. client 개발자가 깜빡할 수 있다 실수로 값을 안보낼 수 있다
        // 2. client bug로 인해 값이 누락될 수 있다
        // 3. 외부의 나쁜 사람이 값을 임의로 조작해서 보낼 수 있다
        // 4. DB에 값을 저장할 때 의도치않은 오류가 발생할 수 있다
        // 5. 서버 개발자의 편안함을 위해서
        if(result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField();      // title
            String errorMessage = firstFieldError.getDefaultMessage();      // error message

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }

//        String title = params.getTitle();
//        if(title == null || title.equals("")) {
//            // 1. 항목이 많을 경우 빡세다
//            // 2. 개발 팁 -> 무언가 3번 이상 반복작업을 할 때 잘못한 건 아닌지 의심한다
//            // 3. 누락될 가능성이 있다
//            // 4. 생각보다 검증해야 할 것이 많다(꼼꼼하지 않을 수 있다)
//            // 5. 뭔가 개발자스럽지 않다 -> 간지X
//            throw new Exception("타이틀값이 없어요");
//        }


        return Map.of();
    }


}
