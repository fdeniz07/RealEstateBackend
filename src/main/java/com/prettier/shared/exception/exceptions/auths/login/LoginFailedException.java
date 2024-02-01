package com.prettier.shared.exception.exceptions.auths.login;

import com.prettier.shared.exception.enums.IFriendlyMessageCode;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

@Slf4j
@Getter
public class LoginFailedException extends AuthenticationException {

    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public LoginFailedException(Language language, IFriendlyMessageCode friendlyMessageCode, String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[LoginFailedException] -> message:{} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode), message);
    }
}