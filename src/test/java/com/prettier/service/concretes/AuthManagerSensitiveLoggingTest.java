package com.prettier.service.concretes;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.entity.enums.Gender;
import com.prettier.payloads.mapper.AuthMapper;
import com.prettier.payloads.request.concretes.LoginRequest;
import com.prettier.payloads.request.concretes.SignUpRequest;
import com.prettier.payloads.response.concretes.LoginResponse;
import com.prettier.payloads.response.concretes.SignUpResponse;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import com.prettier.security.jwt.JWTUtils;
import com.prettier.service.abstracts.RoleService;
import com.prettier.shared.utils.enums.Language;
import com.prettier.shared.utils.validations.CheckUniqueFields;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthManagerSensitiveLoggingTest {

    private static final Language LANG = Language.EN;
    private static final String EMAIL = "customer@example.com";
    private static final String RAW_PASSWORD = "SecretPass1";
    private static final String PASSWORD_HASH = "$2a$10$hashed-password-value";
    private static final String JWT_TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdXN0b21lckBleGFtcGxlLmNvbSJ9.signature";

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private RoleService roleService;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuthMapper authMapper;
    @Mock private CheckUniqueFields checkUniqueFields;
    @Mock private JWTUtils jwtService;
    @Mock private AuthenticationManager authenticationManager;

    private AuthManager authManager;
    private ListAppender<ILoggingEvent> listAppender;
    private Logger authLogger;
    private Level previousLevel;

    @BeforeEach
    void setUp() {
        authManager =
                new AuthManager(
                        userRepository,
                        roleRepository,
                        roleService,
                        passwordEncoder,
                        authMapper,
                        checkUniqueFields,
                        jwtService,
                        authenticationManager);

        authLogger = (Logger) LoggerFactory.getLogger(AuthManager.class);
        previousLevel = authLogger.getLevel();
        authLogger.setLevel(Level.DEBUG);
        listAppender = new ListAppender<>();
        listAppender.start();
        authLogger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        authLogger.detachAppender(listAppender);
        listAppender.stop();
        authLogger.setLevel(previousLevel);
    }

    @Test
    void loginSuccess_doesNotLogJwtPasswordOrHash() {
        User principal = authenticatedUser();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        principal, null, principal.getAuthorities());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateToken(principal)).thenReturn(JWT_TOKEN);

        LoginRequest request =
                LoginRequest.builder().email(EMAIL).password(RAW_PASSWORD).build();

        LoginResponse response = authManager.login(LANG, request);

        assertThat(response.getToken()).isEqualTo(JWT_TOKEN);
        assertLoggedMessagesDoNotContainSecrets();
        assertThat(joinedLogMessages()).contains("success userId:");
        assertThat(joinedLogMessages()).contains(EMAIL);
    }

    @Test
    void signUpSuccess_doesNotLogJwtPasswordOrHash_andOmitsPasswordFromResponse() {
        when(checkUniqueFields.checkDuplicate(eq(LANG), eq(EMAIL), anyString())).thenReturn(false);
        when(passwordEncoder.encode(RAW_PASSWORD)).thenReturn(PASSWORD_HASH);
        Role customer = new Role();
        customer.setName("CUSTOMER");
        when(roleService.getByRoleName("CUSTOMER")).thenReturn(Set.of(customer));
        when(userRepository.saveAndFlush(any(User.class)))
                .thenAnswer(
                        invocation -> {
                            User saved = invocation.getArgument(0);
                            saved.setId(42L);
                            return saved;
                        });
        when(jwtService.generateToken(any(User.class))).thenReturn(JWT_TOKEN);

        SignUpRequest request =
                SignUpRequest.builder()
                        .firstName("Ada")
                        .lastName("Lovelace")
                        .email(EMAIL)
                        .password(RAW_PASSWORD)
                        .phone("000000000001")
                        .gender(Gender.FEMALE)
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .build();

        SignUpResponse response = authManager.signUp(LANG, request);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(JWT_TOKEN);
        assertThat(response.getEmail()).isEqualTo(EMAIL);
        assertLoggedMessagesDoNotContainSecrets();
        assertThat(joinedLogMessages()).contains("success userId:");
        assertThat(joinedLogMessages()).contains(EMAIL);
    }

    private void assertLoggedMessagesDoNotContainSecrets() {
        String logs = joinedLogMessages();
        assertThat(logs).doesNotContain(JWT_TOKEN);
        assertThat(logs).doesNotContain("eyJ");
        assertThat(logs).doesNotContain(RAW_PASSWORD);
        assertThat(logs).doesNotContain(PASSWORD_HASH);
    }

    private String joinedLogMessages() {
        return listAppender.list.stream()
                .map(ILoggingEvent::getFormattedMessage)
                .collect(Collectors.joining("\n"));
    }

    private static User authenticatedUser() {
        User user = new User();
        user.setId(7L);
        user.setEmail(EMAIL);
        user.setPasswordHash(PASSWORD_HASH);
        user.setAuthorities(List.of(new SimpleGrantedAuthority("CUSTOMER")));
        return user;
    }
}
