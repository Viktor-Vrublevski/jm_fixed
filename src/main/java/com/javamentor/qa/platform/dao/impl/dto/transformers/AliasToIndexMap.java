package com.javamentor.qa.platform.dao.impl.dto.transformers;

import java.util.LinkedHashMap;
import java.util.Map;

public class AliasToIndexMap {
    public static Map<String, Integer> get(
            String[] aliases) {

        Map<String, Integer> aliasToIndexMap = new LinkedHashMap<>();

        for (int i = 0; i < aliases.length; i++) {
            aliasToIndexMap.put(aliases[i], i);
        }

        return aliasToIndexMap;
    }
}
