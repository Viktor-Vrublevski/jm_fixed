package com.javamentor.qa.platform.dao.impl.dto.transformers;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;
import org.hibernate.transform.ResultTransformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserDtoListTranformer implements ResultTransformer {

    private Map<Long, UserDtoList> tagDtoWithCountDtoMap = new LinkedHashMap<>();

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {

        Map<String, Integer> aliasToIndexMapTrans = AliasToIndexMap.get(aliases);
        Long userId = ((Number) tuple[0]).longValue();

        UserDtoList userDtoList = tagDtoWithCountDtoMap.computeIfAbsent(
                userId,
                id1 -> {
                    UserDtoList userDtoListTemp = new UserDtoList();
                    userDtoListTemp.setId(((Number) tuple[aliasToIndexMapTrans.get("user_id")]).longValue());
                    userDtoListTemp.setFullName(((String) tuple[aliasToIndexMapTrans.get("full_name")]));
                    userDtoListTemp.setLinkImage(((String) tuple[aliasToIndexMapTrans.get("link_image")]));
                    userDtoListTemp.setReputation(((Number) tuple[aliasToIndexMapTrans.get("reputation")]).intValue());
                    userDtoListTemp.setTags(new ArrayList<>());

                    return userDtoListTemp;
                }
        );

        userDtoList.getTags().add(
                new TagDto(
                        ((Number) tuple[aliasToIndexMapTrans.get("tag_id")]).longValue(),
                        ((String) tuple[aliasToIndexMapTrans.get("tag_name")])
                )
        );

        return userDtoList;
    }

    @Override
    public List transformList(List list) {
        return new ArrayList<>(tagDtoWithCountDtoMap.values());
    }

}
