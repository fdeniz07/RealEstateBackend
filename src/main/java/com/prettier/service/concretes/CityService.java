package com.prettier.service.concretes;

import com.prettier.payload.mapper.CityMapper;
import com.prettier.payload.response.concretes.CityResponse;
import com.prettier.repository.CityRepository;
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
public class CityService implements Serializable {

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    public Page<CityResponse> getAllWithPage(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return cityRepository.findAll(pageable).map(cityMapper::toResponse);
    }
}
