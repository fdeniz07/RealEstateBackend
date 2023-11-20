package com.prettier.entity.concretes;

import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class Category extends BaseEntity implements Serializable {


    @Column(name = "title", nullable = false)
    @Size(max = 150)
    private String title;

    @Column(name = "icon", nullable = false)
    @Size( max = 50)
    private String icon;

    @Column(name = "built_in", nullable = false)
    private boolean builtIn;

    @Column(name = "seq", nullable = false)
    private int seq;

    @Column(name = "slug", nullable = false)
    @Size(min = 5, max = 200)
    private String slug;

    @Column(name = "is_active", nullable = false)
    private boolean isActive=true;

// TODO: iliskiler yapilacak tourrequest,propertykeys,propertyvalues





}
