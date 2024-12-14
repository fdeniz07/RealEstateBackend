package com.prettier.payloads.request.concretes;

import com.prettier.payloads.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class NewMessageRequest extends BaseEntityRequest {

    @NotNull(message = "Please enter subject")
    @Size(min = 5, max = 150, message = "Subject should be between 5 and 150 chars")
    private String subject;

    @NotNull(message = "Please enter message content")
    @Size(min = 5, max = 5000, message = "Message content should be between 5 and 5000 chars")
    private String messageContent;

    @NotNull(message = "Please enter receiver")
    private String receiverMail;

}



