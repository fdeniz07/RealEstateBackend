package com.prettier.service.abstracts;

import com.prettier.payload.request.concretes.DistrictRequest;
import com.prettier.payload.request.concretes.DistrictUpdateRequest;
import com.prettier.payload.response.concretes.DistrictResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface DistrictService extends Serializable {

    Page<DistrictResponse> getDistricts(Language language, int page, int size, String sort, String type);
    DistrictResponse getByDistrictId(Language language, Long id);
    DistrictResponse add(Language language, DistrictRequest districtRequest);
    DistrictResponse update(Language language, DistrictUpdateRequest districtUpdateRequest, Long id);
    DistrictResponse softDelete(Language language, Long id);
}
