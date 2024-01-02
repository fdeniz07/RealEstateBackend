package com.prettier.service.concretes;

import com.prettier.payload.mapper.DistrictMapper;
import com.prettier.payload.response.concretes.DistrictResponse;
import com.prettier.repository.DistrictRepository;
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
public class DistrictManager implements Serializable {

    private final DistrictRepository districtRepository;

    private final DistrictMapper districtMapper;

    public Page<DistrictResponse> getAllWithPage(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return districtRepository.findAll(pageable).map(districtMapper::toResponse);
    }
}
