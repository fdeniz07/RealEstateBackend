package com.prettier.shared.exception.globalExceptionHandling;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.adverts.AdvertAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.adverts.AdvertNotCreatedException;
import com.prettier.shared.exception.exceptions.adverts.AdvertNotFoundException;
import com.prettier.shared.exception.exceptions.auths.login.LoginFailedException;
import com.prettier.shared.exception.exceptions.auths.signUp.EmailAlreadyExistsException;
import com.prettier.shared.exception.exceptions.auths.signUp.PhoneAlreadyExistsException;
import com.prettier.shared.exception.exceptions.auths.signUp.UsernameAlreadyExistsException;
import com.prettier.shared.exception.exceptions.categories.CategoryAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.categories.CategoryAlreadyExistsException;
import com.prettier.shared.exception.exceptions.categories.CategoryNotCreatedException;
import com.prettier.shared.exception.exceptions.categories.CategoryNotFoundException;
import com.prettier.shared.exception.exceptions.categoryPropertyKey.CategoryPropertyKeyAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.categoryPropertyKey.CategoryPropertyKeyAlreadyExistsException;
import com.prettier.shared.exception.exceptions.categoryPropertyKey.CategoryPropertyKeyNotCreatedException;
import com.prettier.shared.exception.exceptions.categoryPropertyKey.CategoryPropertyKeyNotFoundException;
import com.prettier.shared.exception.exceptions.cities.CityAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.cities.CityNotCreatedException;
import com.prettier.shared.exception.exceptions.cities.CityNotFoundException;
import com.prettier.shared.exception.exceptions.countries.CountryAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.countries.CountryAlreadyExistsException;
import com.prettier.shared.exception.exceptions.countries.CountryNotCreatedException;
import com.prettier.shared.exception.exceptions.countries.CountryNotFoundException;
import com.prettier.shared.exception.exceptions.districts.DistrictAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.districts.DistrictAlreadyExistsException;
import com.prettier.shared.exception.exceptions.districts.DistrictNotCreatedException;
import com.prettier.shared.exception.exceptions.districts.DistrictNotFoundException;
import com.prettier.shared.exception.exceptions.tourRequests.TourRequestAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.tourRequests.TourRequestAlreadyExistsException;
import com.prettier.shared.exception.exceptions.tourRequests.TourRequestNotCreatedException;
import com.prettier.shared.exception.exceptions.tourRequests.TourRequestNotFoundException;
import com.prettier.shared.utils.FriendlyMessageUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
//Bu anostasyonun eklenmesinin nedeni burada exception handling yapabilmektir. Ve exception handler'lerimizi tek bir genel hata componentinde birlestirmemize olonak saglar.
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalExceptionHandler {


    /////////////////// ADVERT \\\\\\\\\\\\\\\
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AdvertNotCreatedException.class)
    //Tüm handle class'irimizin üzerine bu anotasyon eklenmeli ki, @RestControllerAdvice ile tek bir yerden yönetelim
    public InternalApiResponse<String> handleAdvertNotCreatedException(AdvertNotCreatedException exception, HttpServletRequest request) {

        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AdvertNotFoundException.class)
    public InternalApiResponse<String> handleAdvertNotFoundException(AdvertNotFoundException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AdvertAlreadyDeletedException.class)
    public InternalApiResponse<String> handleAdvertAlreadyDeletedException(AdvertAlreadyDeletedException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    /////////////////// CITY \\\\\\\\\\\\\\\

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CityNotCreatedException.class)
    public InternalApiResponse<String> handleCityNotCreatedException(CityNotCreatedException exception, HttpServletRequest request) {

        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CityNotFoundException.class)
    public InternalApiResponse<String> handleCityNotFoundException(CityNotFoundException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CityAlreadyDeletedException.class)
    public InternalApiResponse<String> handleCityAlreadyDeletedException(CityAlreadyDeletedException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    /////////////////// COUNTRY \\\\\\\\\\\\\\\

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CountryNotCreatedException.class)
    public InternalApiResponse<String> handleCountryNotCreatedException(CountryNotCreatedException exception, HttpServletRequest request) {

        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CountryNotFoundException.class)
    public InternalApiResponse<String> handleCountryNotFoundException(CountryNotFoundException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CountryAlreadyDeletedException.class)
    public InternalApiResponse<String> handleCountryAlreadyDeletedException(CountryAlreadyDeletedException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CountryAlreadyExistsException.class)
    public InternalApiResponse<String> handleCountryAlreadyExistsException(CountryAlreadyExistsException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.CONFLICT)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    /////////////////// DISTRICT \\\\\\\\\\\\\\\

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DistrictNotCreatedException.class)
    public InternalApiResponse<String> handleDistrictNotCreatedException(DistrictNotCreatedException exception, HttpServletRequest request) {

        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DistrictNotFoundException.class)
    public InternalApiResponse<String> handleDistrictNotFoundException(DistrictNotFoundException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DistrictAlreadyDeletedException.class)
    public InternalApiResponse<String> handleDistrictAlreadyDeletedException(DistrictAlreadyDeletedException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DistrictAlreadyExistsException.class)
    public InternalApiResponse<String> handleDistrictAlreadyExistsException(DistrictAlreadyExistsException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.CONFLICT)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    /////////////////// CATEGORY \\\\\\\\\\\\\\\

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryNotCreatedException.class)
    public InternalApiResponse<String> handleCategoryNotCreatedException(CategoryNotCreatedException exception, HttpServletRequest request) {

        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public InternalApiResponse<String> handleCategoryNotFoundException(CategoryNotFoundException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryAlreadyDeletedException.class)
    public InternalApiResponse<String> handleCategoryAlreadyDeletedException(CategoryAlreadyDeletedException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public InternalApiResponse<String> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.CONFLICT)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    /////////////////// CATEGORYPROPERTYKEY \\\\\\\\\\\\\\\

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryPropertyKeyNotCreatedException.class)
    public InternalApiResponse<String> handleCategoryPropertyKeyNotCreatedException(CategoryPropertyKeyNotCreatedException exception, HttpServletRequest request) {

        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryPropertyKeyNotFoundException.class)
    public InternalApiResponse<String> handleCategoryPropertyKeyNotFoundException(CategoryPropertyKeyNotFoundException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryPropertyKeyAlreadyDeletedException.class)
    public InternalApiResponse<String> handleCategoryPropertyKeyAlreadyDeletedException(CategoryPropertyKeyAlreadyDeletedException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CategoryPropertyKeyAlreadyExistsException.class)
    public InternalApiResponse<String> handleCategoryPropertyKeyAlreadyExistsException(CategoryPropertyKeyAlreadyExistsException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.CONFLICT)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    /////////////////// TOURREQUEST \\\\\\\\\\\\\\\

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TourRequestNotCreatedException.class)
    public InternalApiResponse<String> handleTourRequestNotCreatedException(TourRequestNotCreatedException exception, HttpServletRequest request) {

        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TourRequestNotFoundException.class)
    public InternalApiResponse<String> handleTourRequestNotFoundException(TourRequestNotFoundException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TourRequestAlreadyDeletedException.class)
    public InternalApiResponse<String> handleTourRequestAlreadyDeletedException(TourRequestAlreadyDeletedException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TourRequestAlreadyExistsException.class)
    public InternalApiResponse<String> handleTourRequestAlreadyExistsException(TourRequestAlreadyExistsException exception, HttpServletRequest request) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.CONFLICT)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    /////////////////// AUTH \\\\\\\\\\\\\\\

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public InternalApiResponse<String> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException exception, HttpServletRequest request) {

        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.CONFLICT)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public InternalApiResponse<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception, HttpServletRequest request) {

        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.CONFLICT)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public InternalApiResponse<String> handlePhoneAlreadyExistsException(PhoneAlreadyExistsException exception, HttpServletRequest request) {

        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.CONFLICT)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginFailedException.class)
    public InternalApiResponse<String> handleLoginFailedException(LoginFailedException exception, HttpServletRequest request) {

        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleMethodArgNotValidEx(MethodArgumentNotValidException exception, HttpServletRequest request) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        apiErrorResponse.setHasError(true);
        apiErrorResponse.setErrorMessages("Validation Error");
        apiErrorResponse.setTimeStamp(LocalDateTime.now());
        apiErrorResponse.setPath(request.getRequestURI());
        Map<String, String> validationErrors = new HashMap<>();

        for (var fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiErrorResponse.setValidationErrors(validationErrors);
        return apiErrorResponse;
    }


//    @ExceptionHandler(InsufficientAuthenticationException.class)
//    @ExceptionHandler(BadCredentialsException.class)


}
