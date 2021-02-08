package com.javamentor.qa.platform.dao.impl.dto.transformers;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.hibernate.transform.ResultTransformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuestionResultTransformer implements ResultTransformer {

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
                    questionDtoTemp.setTitle(((String) tuple[aliasToIndexMapTrans.get("question_title")]));

                    questionDtoTemp.setAuthorName(((String) tuple[aliasToIndexMapTrans.get("question_authorName")]));
                    questionDtoTemp.setAuthorId(((Number) tuple[aliasToIndexMapTrans.get("question_authorId")]).longValue());
                    questionDtoTemp.setAuthorImage(((String) tuple[aliasToIndexMapTrans.get("question_authorImage")]));

                    questionDtoTemp.setDescription(((String) tuple[aliasToIndexMapTrans.get("question_description")]));

                    questionDtoTemp.setViewCount(((Number) tuple[aliasToIndexMapTrans.get("question_viewCount")]).intValue());
                    questionDtoTemp.setCountAnswer(((Number) tuple[aliasToIndexMapTrans.get("question_countAnswer")]).intValue());
                    questionDtoTemp.setCountValuable(((Number) tuple[aliasToIndexMapTrans.get("question_countValuable")]).intValue());

                    questionDtoTemp.setPersistDateTime((LocalDateTime) tuple[aliasToIndexMapTrans.get("question_persistDateTime")]);
                    questionDtoTemp.setLastUpdateDateTime((LocalDateTime) tuple[aliasToIndexMapTrans.get("question_lastUpdateDateTime")]);
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
