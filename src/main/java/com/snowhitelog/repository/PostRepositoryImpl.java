package com.snowhitelog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.snowhitelog.domain.Post;

import com.snowhitelog.domain.QPost;
import com.snowhitelog.request.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.snowhitelog.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
