package com.javamentor.qa.platform.dao.impl.dto.transformers;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.hibernate.transform.ResultTransformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuestionResultTransformerTagOnly implements ResultTransformer {

    private Map<Long, QuestionDto> questionDtoMap = new LinkedHashMap<>();

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {

        Map<String, Integer> aliasToIndexMapTrans = AliasToIndexMap.get(aliases);
        Long questionId = ((Number) tuple[0]).longValue();

        QuestionDto questionDto = questionDtoMap.computeIfAbsent(
                questionId,
                id1 -> {
                    QuestionDto questionDtoTemp = new QuestionDto();
                    questionDtoTemp.setId(((Number) tuple[aliasToIndexMapTrans.get("question_id")]).longValue());
                    questionDtoTemp.setListTagDto(new ArrayList<>());
                    return questionDtoTemp;
                }
        );

        questionDto.getListTagDto().add(
                new TagDto(
                        ((Number) tuple[aliasToIndexMapTrans.get("tag_id")]).longValue(),
                        ((String) tuple[aliasToIndexMapTrans.get("tag_name")])
                )
        );

        return questionDto;
    }

    @Override
    public List transformList(List list) {
        return new ArrayList<>(questionDtoMap.values());
    }
}
