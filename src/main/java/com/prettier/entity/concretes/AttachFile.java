package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AttachFile extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Size(min = 2, max = 5)
    private String type;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "featured", nullable = false)
    private boolean featured;

    @ManyToOne
    @JoinColumn(name = "file_id")
    @JsonIgnore //coklu iliskilerde tablonun birinde bu annotation kullanilir, aksi durumda sout yapildiginda sonsuz döngüye girer!
    private Message message;
}
