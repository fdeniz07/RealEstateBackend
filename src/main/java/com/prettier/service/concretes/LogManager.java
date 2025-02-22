package com.prettier.service.concretes;


import com.prettier.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * LogManager.java
 * Service class for managing system logs.
 * Implements Serializable for object serialization support.
 */
@Service
@RequiredArgsConstructor
public class LogManager implements Serializable {

    private final LogRepository logRepository;



}
