package com.prettier.shared.exception.exceptions.users;

import com.prettier.shared.utils.messages.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class ActivationNotificationException extends RuntimeException  {

    public ActivationNotificationException() {
        super(Messages.getMessageForLocale("FriendlyMessageCodes__CREATE_USER.EMAIL.FAILURE", LocaleContextHolder.getLocale()));
    }
}
