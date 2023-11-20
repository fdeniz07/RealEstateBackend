package com.prettier.entity.concretes;

import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;



@Entity
@Table(name = "logs")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString


public class Log extends BaseEntity implements Serializable {


    //Todo ilişkiler sonrası eklenecek


}
