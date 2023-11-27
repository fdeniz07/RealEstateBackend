package com.prettier.service;


import com.prettier.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class LogService implements Serializable {

    private final LogRepository logRepository;



}
