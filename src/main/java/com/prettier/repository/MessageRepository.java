package com.prettier.repository;

import com.prettier.entity.concretes.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.receiver.email=?1")
    Page<Message> findAllByReceiverEquals(String email, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.sender.email=?1")
    Page<Message> findAllBySenderEquals(String email, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.isRead = true")
    Page<Message> findAllByReceiverEqualsAndReadTrue(String email, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.isRead = false")
    Page<Message> findAllByReceiverEqualsAndReadFalse(String email, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.isDraft = true")
    Page<Message> findAllByReceiverEqualsAndDraftTrue(String email, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.isTrash = true")
    Page<Message> findAllByReceiverEqualsAndTrashTrue(String email, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.isImportant = true")
    Page<Message> findAllByReceiverEqualsAndImportantTrue(String email, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.isSpam = true")
    Page<Message> findAllByReceiverEqualsAndSpamTrue(String email, Pageable pageable);

    @Query("SELECT m.sender.email FROM Message m WHERE m.receiver.email =:receiver AND m.sender.email =:sender ")
    Page<Message> findAllBySender(@Param("receiver")String email, @Param("sender") String senderEmail, Pageable pageable);

}
//GROUP BY m.sender.email