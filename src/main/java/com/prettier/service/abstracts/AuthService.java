package com.prettier.service.abstracts;

import com.prettier.payloads.request.concretes.LoginRequest;
import com.prettier.payloads.response.concretes.LoginResponse;
import com.prettier.payloads.request.concretes.SignUpRequest;
import com.prettier.payloads.response.concretes.SignUpResponse;
import com.prettier.shared.utils.enums.Language;

public interface AuthService {

    SignUpResponse signUp(Language language, SignUpRequest signUpRequest);

    LoginResponse login(Language language, LoginRequest loginRequest);
}
