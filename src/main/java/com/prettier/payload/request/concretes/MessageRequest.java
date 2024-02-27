package com.prettier.payload.request.concretes;

import com.prettier.entity.concretes.User;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class MessageRequest  extends BaseEntityRequest {

    @NotNull(message = "Please enter subject")
    @Size(min = 5, max = 150, message = "Subject should be between 5 and 150 chars")
    private String subject;

    @NotNull(message = "Please enter message content")
    @Size(min = 5, max = 5000, message = "Message content should be between 5 and 5000 chars")
    private String messageContent;

    private boolean isDraft;
    private boolean isTrash;
    private boolean isRead;
    private boolean isImportant;
    private boolean isSpam;

    @NotNull(message = "Please enter receiver")
    private String receiverMail;

}



