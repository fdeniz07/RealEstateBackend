package com.prettier.service.concretes;

import com.prettier.payloads.request.concretes.TourRequestRequest;
import com.prettier.payloads.request.concretes.TourRequestUpdateRequest;
import com.prettier.payloads.response.concretes.TourRequestResponse;
import com.prettier.repository.TourRequestRepository;
import com.prettier.service.abstracts.TourRequestService;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TourRequestManager implements TourRequestService {

   private final TourRequestRepository tourRequestRepository;

   @Override
   public Page<TourRequestResponse> getTourRequestsForCustomer(Language language, TourRequestRequest tourRequestRequest) {
      return null;
   }

   @Override
   public Page<TourRequestResponse> getTourRequestForManager(Language language, TourRequestRequest tourRequestRequest) {
      return null;
   }

   @Override
   public TourRequestResponse getByTourRequestIdForCustomer(Language language, Long id) {
      return null;
   }

   @Override
   public TourRequestResponse getByTourRequestIdForManager(Language language, Long id) {
      return null;
   }

   @Override
   public TourRequestResponse add(Language language, TourRequestRequest districtRequest) {
      return null;
   }

   @Override
   public TourRequestResponse update(Language language, TourRequestUpdateRequest tourRequestUpdateRequest, Long id) {
      return null;
   }

   @Override
   public TourRequestResponse cancel(Language language, TourRequestUpdateRequest tourRequestUpdateRequest, Long id) {
      return null;
   }

   @Override
   public TourRequestResponse approve(Language language, TourRequestUpdateRequest tourRequestUpdateRequest, Long id) {
      return null;
   }

   @Override
   public TourRequestResponse decline(Language language, TourRequestUpdateRequest tourRequestUpdateRequest, Long id) {
      return null;
   }

   @Override
   public TourRequestResponse softDelete(Language language, Long id) {
      return null;
   }
}
