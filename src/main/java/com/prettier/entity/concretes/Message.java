package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class Message extends BaseEntity {

    @Column(nullable = false)
    private String subject;

    @Column(name = "message_content", nullable = false)
    private String messageContent;

    @Column(name = "is_draft")
    private boolean isDraft;

    @Column(name = "is_trash")
    private boolean isTrash;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "is_important")
    private boolean isImportant;

    @Column(name = "is_spam")
    private boolean isSpam;

    @ManyToOne
    @JoinColumn(name = "sender_user_id", nullable = false)
    @JsonIgnore
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id", nullable = false)
    @JsonIgnore
    private User receiver;

    @OneToMany(mappedBy = "file")
    @ToString.Exclude
    private Set<File> fileSet;
}
