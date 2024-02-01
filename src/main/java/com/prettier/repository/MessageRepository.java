package com.prettier.repository;

import com.prettier.entity.concretes.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {


}
