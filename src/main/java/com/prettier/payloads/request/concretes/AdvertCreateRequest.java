package com.prettier.payloads.request.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Client payload for creating an advertisement. Ownership, slug and status are assigned by the service.
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdvertCreateRequest implements Serializable {

    @NotBlank(message = "Please enter content language")
    private String contentLanguage;

    @NotNull(message = "Please enter title")
    @Size(min = 5, max = 150, message = "Your title should be between 5 and 150 chars")
    private String title;

    @Size(max = 300, message = "Your description should be at most 300 chars")
    private String desc;

    @NotNull(message = "Please enter price")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @Size(max = 255, message = "Location should be at most 255 chars")
    private String location;

    @NotNull(message = "Please select advert type")
    @Positive(message = "Advert type id must be a positive number")
    private Long advertTypeId;

    @NotNull(message = "Please select country")
    @Positive(message = "Country id must be a positive number")
    private Long countryId;

    @NotNull(message = "Please select city")
    @Positive(message = "City id must be a positive number")
    private Long cityId;

    @NotNull(message = "Please select district")
    @Positive(message = "District id must be a positive number")
    private Long districtId;

    @NotNull(message = "Please select category")
    @Positive(message = "Category id must be a positive number")
    private Long categoryId;
}
