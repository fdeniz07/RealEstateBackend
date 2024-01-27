package com.prettier.service.abstracts;

import com.prettier.payload.request.concretes.LoginRequest;
import com.prettier.payload.response.concretes.LoginResponse;
import com.prettier.payload.request.concretes.SignUpRequest;
import com.prettier.payload.response.concretes.SignUpResponse;
import com.prettier.shared.utils.enums.Language;

public interface AuthService {

    SignUpResponse signUp(Language language, SignUpRequest signUpRequest);

    LoginResponse login(Language language, LoginRequest loginRequest);
}
