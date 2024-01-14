package com.prettier.repository;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.TourRequest;
import com.prettier.entity.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourRequestRepository extends JpaRepository<TourRequest,Long> {

    Optional<Advert> getTourRequestByAdvert_Id(Long advertId);
    Optional<User> getTourRequestByGuestUser_Id(Long userId);
    Optional<User> getTourRequestByOwnerUser_Id(Long userId);
}
