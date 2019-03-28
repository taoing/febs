package com.taoing.system.dao;

import com.taoing.common.config.MyMapper;
import com.taoing.system.domain.Dict;

import java.util.List;

public interface DictMapper extends MyMapper<Dict> {

    List<Dict> findDictByFieldName(String fieldName);

    Dict findDictByFieldNameAndKeyy(String fieldName, String keyy);
}
