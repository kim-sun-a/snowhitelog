package com.snowhitelog.repository;

import com.snowhitelog.domain.Post;
import com.snowhitelog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
