package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "social_media")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class Social extends BaseEntity {

    @Column(name = "platform_name", nullable = false)
    private String platformName;

    @Column(name = "account_url", nullable = false)
    private String accountUrl;

    @Column(name = "logo", nullable = false)
    private String image;

    @Column(nullable = false)
    private String thumbnail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
