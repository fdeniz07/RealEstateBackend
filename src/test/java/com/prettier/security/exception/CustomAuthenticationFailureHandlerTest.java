package com.prettier.security.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Contract tests for {@link CustomAuthenticationFailureHandler} JSON error responses (Jackson 3).
 */
class CustomAuthenticationFailureHandlerTest {

    private static final String REQUEST_PATH = "/authenticate/login";

    private JsonMapper jsonMapper;
    private CustomAuthenticationFailureHandler handler;

    @BeforeEach
    void setUp() {
        jsonMapper = JsonMapper.builder().findAndAddModules().build();
        handler = new CustomAuthenticationFailureHandler(jsonMapper);
    }

    @Test
    void onAuthenticationFailure_setsUnauthorizedStatus() throws Exception {
        MockHttpServletResponse response = invokeFailure(new BadCredentialsException("ignored"));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void onAuthenticationFailure_setsJsonContentType() throws Exception {
        MockHttpServletResponse response = invokeFailure(new BadCredentialsException("ignored"));

        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    void onAuthenticationFailure_jsonContainsExpectedFields() throws Exception {
        JsonNode body = parseResponseBody(invokeFailure(new BadCredentialsException("ignored")));

        assertThat(body.has("status")).isTrue();
        assertThat(body.has("error")).isTrue();
        assertThat(body.has("timestamp")).isTrue();
        assertThat(body.has("path")).isTrue();
    }

    @Test
    void onAuthenticationFailure_statusFieldIs401() throws Exception {
        JsonNode body = parseResponseBody(invokeFailure(new BadCredentialsException("ignored")));

        assertThat(body.get("status").intValue()).isEqualTo(401);
    }

    @Test
    void onAuthenticationFailure_errorFieldIsUnauthorized() throws Exception {
        JsonNode body = parseResponseBody(invokeFailure(new BadCredentialsException("ignored")));

        assertThat(body.get("error").stringValue()).isEqualTo("Unauthorized");
    }

    @Test
    void onAuthenticationFailure_pathMatchesServletPath() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(REQUEST_PATH);
        MockHttpServletResponse response = new MockHttpServletResponse();

        handler.onAuthenticationFailure(request, response, new BadCredentialsException("ignored"));

        JsonNode body = parseResponseBody(response);
        assertThat(body.get("path").stringValue()).isEqualTo(REQUEST_PATH);
    }

    @Test
    void onAuthenticationFailure_timestampUsesInstanceLifecycle_notPerRequest() throws Exception {
        JsonNode first = parseResponseBody(invokeFailure(new BadCredentialsException("a")));
        JsonNode second = parseResponseBody(invokeFailure(new BadCredentialsException("b")));

        assertThat(second.get("timestamp").stringValue()).isEqualTo(first.get("timestamp").stringValue());
    }

    @Test
    void onAuthenticationFailure_timestampSerializesAsParseableInstant() throws Exception {
        JsonNode body = parseResponseBody(invokeFailure(new BadCredentialsException("ignored")));
        String timestampText = body.get("timestamp").stringValue();

        assertThat(LocalDateTime.parse(timestampText)).isNotNull();
    }

    private MockHttpServletResponse invokeFailure(BadCredentialsException exception) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(REQUEST_PATH);
        MockHttpServletResponse response = new MockHttpServletResponse();
        handler.onAuthenticationFailure(request, response, exception);
        return response;
    }

    private JsonNode parseResponseBody(MockHttpServletResponse response) throws Exception {
        return jsonMapper.readTree(response.getContentAsByteArray());
    }
}