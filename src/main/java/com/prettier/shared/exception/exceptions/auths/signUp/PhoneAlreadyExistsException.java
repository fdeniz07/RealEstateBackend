package com.prettier.shared.exception.exceptions.auths.signUp;

import com.prettier.shared.exception.enums.IFriendlyMessageCode;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;

@Slf4j
@Getter
public class PhoneAlreadyExistsException extends InsufficientAuthenticationException {

    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public PhoneAlreadyExistsException(Language language, IFriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[PhoneAlreadyExistsException] -> message:{} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode), message);
    }
}