package com.prettier.service.abstracts;

import com.prettier.entity.concretes.User;
import com.prettier.payload.request.concretes.SignUpRequest;
import com.prettier.payload.response.concretes.SignUpResponse;
import com.prettier.shared.utils.enums.Language;

public interface AuthService {

    SignUpResponse signUp(Language language, SignUpRequest signUpRequest);
}
