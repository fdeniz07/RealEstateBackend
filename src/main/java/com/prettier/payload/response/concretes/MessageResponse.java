package com.prettier.payload.response.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.entity.concretes.User;
import com.prettier.payload.response.abstracts.BaseEntityResponse;
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
    private boolean isDraft;
    private boolean isTrash;
    private boolean isRead;
    private boolean isImportant;
    private boolean isSpam;
    private String senderMail;
    private String receiverMail;

}
