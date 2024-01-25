package com.prettier.entity.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass //Db de user tablosu olusmadan bu sinifin anac sinif olarak kullanilmasini saglar. DB de tablo olusmaz ama bu classi miras alan siniflar db de buradaki field lere sahip olurlar
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder //Miras alan child class'larin buradaki field leri kullanmasina izin verir. Hem parent a hem de child de SuperBuilder annotation yazilmalidir.
@EntityListeners(AuditingEntityListener.class)
//Auditing: You've included auditing information using @CreatedDate and @EntityListeners(AuditingEntityListener.class). This is useful for tracking when entities are created and updated.

public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_at", nullable = false)
//    @CreatedDate
//    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss")
    private LocalDateTime createAt;

//    @LastModifiedDate
//    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss")
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "is_deleted")
    private boolean deleted;
}
