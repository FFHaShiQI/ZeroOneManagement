package com.hajimi.backend.service.serviceImpl;

import com.hajimi.backend.mapper.SeniorGroupMapper;
import com.hajimi.backend.service.SeniorGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SeniorGroupServiceImpl implements SeniorGroupService {

    @Autowired
    private SeniorGroupMapper seniorGroupMapper;
}
