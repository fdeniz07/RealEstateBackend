package com.prettier.payload.request.concretes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.User;
import com.prettier.entity.enums.TourRequestStatus;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TourRequestRequest extends BaseEntityRequest {

    @NotNull(message = "Please enter tour date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    private LocalDateTime tourDate;

    @NotNull(message = "Please enter tour time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    private LocalDateTime tourTime;

    @NotNull(message = "Please select status type")
    @Enumerated(EnumType.ORDINAL)
    private TourRequestStatus status = TourRequestStatus.PENDING;

    @NotNull(message = "Please select advert")
    @Size(min=1, message ="Advert must not be empty")
    private Long advertId;

    @NotNull(message = "Please select owner user")
    @Size(min=1, message ="Owner user must not be empty")
    private Long ownerUserId;

    @NotNull(message = "Please select guest user")
    @Size(min=1, message ="Gust user must not be empty")
    private Long guestUserId;
}
