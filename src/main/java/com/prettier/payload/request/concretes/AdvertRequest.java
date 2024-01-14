package com.prettier.payload.request.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.concretes.*;
import com.prettier.entity.enums.AdvertStatus;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
public class AdvertRequest extends BaseEntityRequest{

    @NotNull(message = "Please enter title")
    @Size(min = 5, max = 150, message = "Your title should be between 5 and 150 chars")
    private String title;

    @Size(max = 300)
    private String desc;

    @Size(min = 5, max = 200)
    private String slug; //!!! TODO: During Mapping must be title with Url encoded !!!

    private Double price;

    private AdvertStatus advertStatus =AdvertStatus.PENDING;

    @JsonIgnore
    private boolean builtIn;

    @JsonIgnore
    private boolean isActive=true;

    private Integer viewCount = 0; //!!! TODO: Service islemlerinde her görüntülendiginde degeri artacak !!!

    private String location;

    private AdvertType advertType;

    private Country country;

    private City city;

    private District district;

    private User user;

    private Category category;

    private Set<Favorite> favoriteSet;

    private Set<Image> imageSet;

    private Set<TourRequest> tourRequestSet;

    private Set<Log> logSet;

    private Set<CategoryPropertyValue> categoryPropertyValueSet;
}
