package com.insigma.mvc.dao.resources;

import com.insigma.mvc.model.EE18;

public interface EE18Mapper {
    int deleteByPrimaryKey(String eee181);

    int insert(EE18 record);

    int insertSelective(EE18 record);

    EE18 selectByPrimaryKey(String eee181);

    int updateByPrimaryKeySelective(EE18 record);

    int updateByPrimaryKey(EE18 record);
}