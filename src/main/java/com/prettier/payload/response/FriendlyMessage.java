package com.prettier.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)//Json icindeki null olanlarin gözükmemesini sagliyoruz
public class FriendlyMessage { //Generic yapilar bize loose coupling bir yapi olusturmamizi sagliyor. Gelismis bir polymorphism sagliyor.

    private String title;
    private String description;
    private String buttonPositive;

}
