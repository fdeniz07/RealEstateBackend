package com.prettier.entity.enums;

import lombok.Getter;

@Getter
public enum LogAction {

    CREATED("Advert is created and wait for approve"),
    UPDATED("Advert isupdated"),
    DELETED(" Advert is deleted"),
    DECLINED ("Advert is declineddeclinedby manager"),
    TOUR_REQUEST_CREATED("Tour request is created"),
    TOUR_REQUEST_ACCEPTED("Tour request is accepted"),
    TOUR_REQUEST_DECLINED("Tour request is declined"),
    TOUR_REQUEST_CANCELED("Tour request is canceled");

    private final String description;

    LogAction(String description) {
        this.description = description;
    }
}
