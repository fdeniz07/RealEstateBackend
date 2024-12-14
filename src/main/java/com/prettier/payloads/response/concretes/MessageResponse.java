package com.prettier.payloads.response.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.payloads.response.abstracts.BaseEntityResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse  extends BaseEntityResponse {

    private String subject;
    private String messageContent;
    private String senderMail;
    private String receiverMail;
}
