package com.prettier.service.abstracts;

import com.prettier.entity.concretes.District;
import com.prettier.payloads.request.concretes.DistrictRequest;
import com.prettier.payloads.request.concretes.DistrictUpdateRequest;
import com.prettier.payloads.response.concretes.DistrictResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface DistrictService extends Serializable {

    //NOT: *********** Data Inilitalizer icin gerekli metotlar *************************

    List<District> getAllDistricts();


    //NOT: *********** District Manager standart metotlar *************************************


    Page<DistrictResponse> getDistricts(Language language, int page, int size, String sort, String type);

    DistrictResponse getByDistrictId(Language language, Long id);

    DistrictResponse add(Language language, DistrictRequest districtRequest);

    DistrictResponse update(Language language, DistrictUpdateRequest districtUpdateRequest, Long id);

    DistrictResponse softDelete(Language language, Long id);
}
