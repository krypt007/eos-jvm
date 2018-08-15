package com.memtrip.eosio.gen;

import com.memtrip.eosio.model.AbiModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RootMap implements DataMap {

    private final List<AbiModel> abiModelList;

    RootMap(List<AbiModel> abiModelList) {
        this.abiModelList = abiModelList;
    }

    @Override
    public Map<String, Object> map() {
        Map<String, Object> map = new HashMap<>();
        map.put("abi_list", abiModelList);
        return map;
    }
}
