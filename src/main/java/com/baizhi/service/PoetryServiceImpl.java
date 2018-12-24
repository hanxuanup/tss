package com.baizhi.service;


import com.baizhi.dao.PoetryDAO;
import com.baizhi.entity.Poetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PoetryServiceImpl implements PoertyService {

    @Autowired
    private PoetryDAO poetryDAO;

    @Override
    public List<Poetry> queryAll() {
        return poetryDAO.findAll();
    }
}
