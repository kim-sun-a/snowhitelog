package com.snowhitelog.repository;

import com.snowhitelog.domain.Session;
import com.snowhitelog.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, Long> {
}
