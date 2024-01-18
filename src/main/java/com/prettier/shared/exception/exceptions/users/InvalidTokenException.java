package com.prettier.shared.exception.exceptions.users;

import com.prettier.shared.utils.messages.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super(Messages.getMessageForLocale("FriendlyMessageCodes__ACTIVATE_USER_INVALID_TOKEN", LocaleContextHolder.getLocale()));
    }
}
