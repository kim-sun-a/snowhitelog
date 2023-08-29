package com.snowhitelog.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

    Optional<Session> findByAccessToken(String accessToken);
}
