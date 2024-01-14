package com.prettier.service.abstracts;

import com.prettier.payload.request.concretes.TourRequestRequest;
import com.prettier.payload.request.concretes.TourRequestUpdateRequest;
import com.prettier.payload.response.concretes.TourRequestResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface TourRequestService extends Serializable {

    Page<TourRequestResponse> getTourRequestsForCustomer(Language language, TourRequestRequest tourRequestRequest);

    Page<TourRequestResponse> getTourRequestForManager(Language language, TourRequestRequest tourRequestRequest);

    TourRequestResponse getByTourRequestIdForCustomer(Language language, Long id);

    TourRequestResponse getByTourRequestIdForManager(Language language, Long id);

    TourRequestResponse add(Language language, TourRequestRequest districtRequest);

    TourRequestResponse update(Language language, TourRequestUpdateRequest tourRequestUpdateRequest, Long id);

    TourRequestResponse cancel(Language language, TourRequestUpdateRequest tourRequestUpdateRequest, Long id);

    TourRequestResponse approve(Language language, TourRequestUpdateRequest tourRequestUpdateRequest, Long id);

    TourRequestResponse decline(Language language, TourRequestUpdateRequest tourRequestUpdateRequest, Long id);

    TourRequestResponse softDelete(Language language, Long id);
}
