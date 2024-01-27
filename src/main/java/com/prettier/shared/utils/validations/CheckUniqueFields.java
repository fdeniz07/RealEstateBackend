package com.prettier.shared.utils.validations;

import com.prettier.repository.UserRepository;

import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.users.ConflictException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component //DI icin eklendi
@RequiredArgsConstructor
@Slf4j
public class CheckUniqueFields {

    private final UserRepository userRepository;

    public boolean checkDuplicate(Language language, String... values) { //varargs kullanimi sayesinde istedigimiz kadar parametre girebiliriz
        String parameter1 = values[0];
        String parameter2 = values[1];
        String parameter3 = "";

        if (values.length == 3) {
            parameter3 = values[2];
        }

        if (userRepository.existsByUsername(parameter1)) {

            throw new ConflictException(language, FriendlyMessageCodes.USER_NAME_ALREADY_EXISTS, "user request: " + parameter1);

        } else if (userRepository.existsByEmail(parameter2)) {

            throw new ConflictException(language, FriendlyMessageCodes.EMAIl_ALREADY_EXISTS, "user request: " + parameter2);

        } else if (userRepository.existsByPhone(parameter3)) {

            throw new ConflictException(language, FriendlyMessageCodes.PHONE_NUMBER_ALREADY_EXISTS, "user request: " + parameter3);

        }
        return false;
    }
}