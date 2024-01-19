package com.prettier.shared.exception.exceptions.users;

import com.prettier.shared.exception.enums.IFriendlyMessageCode;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import com.prettier.shared.utils.messages.Messages;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Getter
public class NotUniqueEmailException extends RuntimeException {

    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public NotUniqueEmailException(Language language, IFriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[NotUniqueEmailException] -> message:{} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode), message);
    }

//    public NotUniqueEmailException() {
//        super(Messages.getMessageForLocale("FriendlyMessageCodes__ERROR_VALIDATION", LocaleContextHolder.getLocale()));
//    }
//
//    public Map<String,String> getValidationErrors(){
//
//        return Collections.singletonMap("email",Messages.getMessageForLocale("FriendlyMessageCodes__CONSTRAINT_EMAIL_NOT_UNIQUE", LocaleContextHolder.getLocale()));
//    }
}
