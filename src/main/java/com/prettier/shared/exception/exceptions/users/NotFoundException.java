package com.prettier.shared.exception.exceptions.users;

import com.prettier.shared.utils.messages.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class NotFoundException extends RuntimeException {

    public NotFoundException(long id){
        super(Messages.getMessageForLocale("FriendlyMessageCodes__USER_NOT_FOUND", LocaleContextHolder.getLocale(), id));
    }
}