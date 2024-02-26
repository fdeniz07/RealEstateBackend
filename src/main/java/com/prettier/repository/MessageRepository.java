package com.prettier.repository;

import com.prettier.entity.concretes.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {

//    @Query("SELECT m FROM Message m WHERE m.receiver.id = ?1")
//    Page<Message> findByReceiver(Long userId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.sender.email=?1")
    Page<Message> findAllByReceiverEquals(String email, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.isRead = true")
    Page<Message> findAllByReceiverEqualsAndReadTrue(String email, Pageable pageable);



    Page<Message> findAllBySenderEquals(String email, Pageable pageable);
}
