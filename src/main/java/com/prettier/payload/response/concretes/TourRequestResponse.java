package com.prettier.payload.response.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.entity.enums.TourRequestStatus;
import com.prettier.payload.response.abstracts.BaseEntityResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TourRequestResponse extends BaseEntityResponse {

    private LocalDateTime tourDate;

    private LocalDateTime tourTime;

    private TourRequestStatus status; //!!!TODO : Enum deger kontrol edilecek

    private Long advertId;

    private Long ownerUserId;

    private Long guestUserId;
}
