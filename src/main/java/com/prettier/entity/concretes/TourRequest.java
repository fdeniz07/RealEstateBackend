package com.prettier.entity.concretes;


import com.prettier.entity.abstracts.BaseEntity;
import com.prettier.entity.enums.TourRequestStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tour_requests")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString


public class TourRequest extends BaseEntity implements Serializable {

private LocalDateTime tourDate;
private LocalDateTime tourTime;

@Enumerated(EnumType.ORDINAL)
private TourRequestStatus status = TourRequestStatus.PENDING;


@ManyToOne
@JoinColumn(name="advert_id", nullable = false)
private Advert advert;


@ManyToOne
@JoinColumn(name="owner_user_id", nullable = false)
private User ownerUser;

@ManyToOne
@JoinColumn(name="guest_user_id", nullable = false)
private User guestUser;









}
