package com.prettier.payloads.request.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.concretes.*;
import com.prettier.entity.enums.AdvertStatus;
import com.prettier.payloads.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
public class AdvertUpdateRequest extends BaseEntityRequest {

    private Long Id;

    @Size(min = 5, max = 150)
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
