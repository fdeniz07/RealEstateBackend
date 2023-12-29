package com.prettier.service.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.payload.mapper.AdvertMapper;
import com.prettier.payload.request.concretes.AdvertRequest;
import com.prettier.payload.response.FriendlyResponseMessage;
import com.prettier.payload.response.concretes.AdvertResponse;
import com.prettier.repository.AdvertRepository;
import com.prettier.service.abstracts.IAdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdvertService implements IAdvertService, Serializable {

    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;

    @Override
    public Page<AdvertResponse> getAll(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return advertRepository.findAll(pageable).map(advertMapper::toResponse);
    }

    @Override
    public Page<AdvertResponse> getListWithActive(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return advertRepository.findAllActive(pageable).map(advertMapper::toResponse);
    }

    @Override
    public FriendlyResponseMessage<AdvertResponse> getByIdAllType(Long id) {
        return null;
    }

    @Override
    public FriendlyResponseMessage<AdvertResponse> getByIdActive(Long id) {
        return null;
    }

    @Override
    public FriendlyResponseMessage<AdvertResponse> add(AdvertRequest advertRequest) {
        return null;
    }

    @Override
    public FriendlyResponseMessage<AdvertResponse> update(AdvertRequest advertRequest, Advert existAdvert, Long id) {
        return null;
    }

    @Override
    public FriendlyResponseMessage<AdvertResponse> delete(AdvertRequest advertRequest, Long Id) {
        return null;
    }
}
