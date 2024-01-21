package com.prettier.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    ADMIN("ADMIN","Manage everything"),
    MANAGER("MANAGER","Manage under Admin customers,roles and more"),
    CUSTOMER("CUSTOMER","Create a new Advert"),
    PERSONAL("PERSONAL","This is a test role");

    private final String roleName;
    private final String description;

}
