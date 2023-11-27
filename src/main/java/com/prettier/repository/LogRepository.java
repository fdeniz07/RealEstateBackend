package com.prettier.repository;

import com.prettier.entity.concretes.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log,Long> {
}
