package com.prettier.service.concretes;

import com.prettier.repository.TourRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class TourRequestManager implements Serializable {

   private final TourRequestRepository tourRequestRepository;

}
