package com.prettier.service.concretes;

import com.prettier.entity.concretes.City;
import com.prettier.entity.concretes.User;
import com.prettier.payload.mapper.UserMapper;
import com.prettier.payload.request.concretes.UserUpdateRequest;
import com.prettier.payload.response.concretes.UserResponse;
import com.prettier.repository.UserRepository;
import com.prettier.service.abstracts.UserService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.cities.CityAlreadyExistsException;
import com.prettier.shared.exception.exceptions.cities.CityNotFoundException;
import com.prettier.shared.exception.exceptions.users.UserNotFoundException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //Not: getUserById() ******************************************************************************************************
    @Override
    public UserResponse getUserById(Language language, Long id) {

        log.debug("[{}][getUserById] -> request userId: {}", this.getClass().getSimpleName(), id);

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND_EXCEPTION, "User not found for user id: " + id));

        log.debug("[{}][getUserById] -> response: {}", this.getClass().getSimpleName(), user);
        return userMapper.toResponse(user);
    }

    //Not: updateUser() *******************************************************************************************************
    @Override
    public UserResponse update(Language language, UserUpdateRequest userUpdateRequest, Long id) {

        log.debug("[{}][updateUser] -> request: {} {}", this.getClass().getSimpleName(), id, userUpdateRequest);

        //City gercekte db de var mi kontrolü
        User existingUser = getUser(language, id);

        //Güncelleme islemini yap
        userMapper.toUpdatedUser(userUpdateRequest, existingUser);

        // Veritabanına güncellenmiş City'yi kaydet
        User updatedUser = userRepository.save(existingUser);

        log.debug("[{}][updateUser] -> response: {}", this.getClass().getSimpleName(), updatedUser);
        return userMapper.toResponse(updatedUser);
    }

    //Not: uploadProfileImage() ***********************************************************************************************
    @Override
    public UserResponse uploadUserProfileImage(Language language, Long id, MultipartFile file) {
        return null;
    }

    //Not: getProfileImage() **************************************************************************************************
    @Override
    public UserResponse getUserProfileImage(Language language, Long id) {
        return null;
    }

    //Not: deleteUser() *******************************************************************************************************
    @Override
    public UserResponse softDelete(Language language, Long id) {
        return null;
    }

    //Not: Other *********************************************************************************************************************************

    //!!! Ilgili Id, User tablosunda var mi kontrolü
    public User getUser(Language language, Long userId) {

        log.debug("[{}][getUser] -> request cityId: {}", this.getClass().getSimpleName(), userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND_EXCEPTION, "User not found for city id: " + userId));

        log.debug("[{}][getUser] -> response: {}", this.getClass().getSimpleName(), user);
        return user;
    }

    //!!! Ilgili UserName, User tablosunda var mi kontrolü
    public boolean existsByUserName(Language language, String email) {

        log.debug("[{}][getUser] -> request cityName: {}", this.getClass().getSimpleName(), email);
        if (userRepository.existsByEmail(email)) {
            throw new CityAlreadyExistsException(language, FriendlyMessageCodes.EMAIl_ALREADY_EXISTS, "This Email already exists for username: " + email);
        }

        log.debug("[{}][getUser] -> response: {}", this.getClass().getSimpleName(), email);
        return false;
    }


    public User getUserByEmail(Language language, String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND_EXCEPTION, email));

        return user;
    }

    public String getUserMailById(Long id) {

        return userRepository.findEmailById(id);
    }
}
