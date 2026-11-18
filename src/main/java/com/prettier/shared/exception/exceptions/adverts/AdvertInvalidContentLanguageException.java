package com.prettier.shared.exception.exceptions.adverts;

import com.prettier.shared.exception.enums.IFriendlyMessageCode;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AdvertInvalidContentLanguageException extends RuntimeException {

    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public AdvertInvalidContentLanguageException(
            Language language, IFriendlyMessageCode friendlyMessageCode, String developerMessage) {
        super(FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error(
                "[AdvertInvalidContentLanguageException] -> message:{} developer message: {}",
                getMessage(),
                developerMessage);
    }
}