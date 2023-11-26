package com.prettier.service;

import com.prettier.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class AdviceService implements Serializable {

    private final AdvertRepository advertRepository;
}
