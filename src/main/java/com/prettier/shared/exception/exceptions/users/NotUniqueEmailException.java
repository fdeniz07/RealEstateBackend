package com.prettier.shared.exception.exceptions.users;

import com.prettier.shared.utils.messages.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Collections;
import java.util.Map;

public class NotUniqueEmailException extends RuntimeException {

    public NotUniqueEmailException() {
        super(Messages.getMessageForLocale("FriendlyMessageCodes__ERROR_VALIDATION", LocaleContextHolder.getLocale()));
    }

    public Map<String,String> getValidationErrors(){

        return Collections.singletonMap("email",Messages.getMessageForLocale("FriendlyMessageCodes__CONSTRAINT_EMAIL_NOT_UNIQUE", LocaleContextHolder.getLocale()));
    }
}
