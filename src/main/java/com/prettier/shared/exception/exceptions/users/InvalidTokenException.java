package com.prettier.shared.exception.exceptions.users;

import com.prettier.shared.exception.enums.IFriendlyMessageCode;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import com.prettier.shared.utils.messages.Messages;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;

@Slf4j
@Getter
public class InvalidTokenException extends RuntimeException {

    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public InvalidTokenException(Language language, IFriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[InvalidTokenException] -> message:{} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode), message);
      //  super(Messages.getMessageForLocale("FriendlyMessageCodes__ACTIVATE_USER_INVALID_TOKEN", LocaleContextHolder.getLocale()));
    }
}
