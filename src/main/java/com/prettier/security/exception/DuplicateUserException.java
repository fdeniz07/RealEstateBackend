package com.prettier.security.exception;

import com.prettier.shared.exception.enums.IFriendlyMessageCode;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;

@Getter
@Setter
@Slf4j
public class DuplicateUserException extends AuthenticationException {

    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public DuplicateUserException(Language language, IFriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[CustomAuthenticationException] -> message:{} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode), message);
    }

    public DuplicateUserException(String msg, Throwable cause, Language language, IFriendlyMessageCode friendlyMessageCode) {
        super(msg, cause);
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
    }

    public DuplicateUserException(String msg, Language language, IFriendlyMessageCode friendlyMessageCode) {
        super(msg);
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
    }
}
