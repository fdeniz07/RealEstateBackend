package com.prettier.entity.concretes;

import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class Image extends BaseEntity implements Serializable {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Size(min = 2, max = 5)
    private String type;

    @Column(name = "path", nullable = false)
    private String path;

//    @Lob
//    private Byte[] data;

    @Column(name = "featured", nullable = false)
    private boolean featured;

    @ManyToOne
    @JoinColumn(name = "advert_id", nullable = false)
    private Advert advert;
}
