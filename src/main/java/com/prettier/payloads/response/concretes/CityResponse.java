package com.prettier.payloads.response.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.payloads.response.abstracts.BaseEntityResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
//@ToString(callSuper = true) // normalde toString metodu ilgili sinifin field'larini yazririr, callsuper ile basedeki fieldlari da yazdirmaya yarar
public class CityResponse extends BaseEntityResponse {

    private String name;
    private CountryResponse countryResponse;
}
