package com.javamentor.qa.platform.dao.impl.dto.pagination.questions;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.impl.dto.transformers.QuestionResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository(value = "paginationQuestionBySearchValue")
@SuppressWarnings(value = "unchecked")
public class PaginationQuestionBySearchValueDaoImpl implements PaginationDao<QuestionDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<QuestionDto> getItems(Map<String, Object> parameters) {

        int page = (int) parameters.get("page");
        int size = (int) parameters.get("size");
        Map<String, String> data = createQuery((String)parameters.get("message"));

        return (List<QuestionDto>) em.unwrap(Session.class)
                .createQuery(data.get("query"))
                .setParameter("userId", data.get("author").isEmpty() ? 0 : Long.parseLong(data.get("author")))
                .setParameter("views", data.get("views").isEmpty() ? 0 : Integer.parseInt(data.get("views")))
                .setParameter("answers", data.get("answers").isEmpty() ? 0 : Long.parseLong(data.get("answers")))
                .setParameter("exactly", data.get("exactSearch").isEmpty() ? "%" : "%" + data.get("exactSearch") + "%")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .unwrap(Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .getResultList();
    }

    @Override
    public int getCount(Map<String, Object> parameters) {
        Map<String, String> data = createQuery((String)parameters.get("message"));

        return em.unwrap(Session.class)
                .createQuery(data.get("query"))
                .setParameter("userId", data.get("author").isEmpty() ? 0 : Long.parseLong(data.get("author")))
                .setParameter("views", data.get("views").isEmpty() ? 0 : Integer.parseInt(data.get("views")))
                .setParameter("answers", data.get("answers").isEmpty() ? 0 : Long.parseLong(data.get("answers")))
                .setParameter("exactly", data.get("exactSearch").isEmpty() ? "%" : "%" + data.get("exactSearch") + "%")
                .unwrap(Query.class)
                .setResultTransformer(new QuestionResultTransformer())
                .getResultList().size();
    }

    private Map<String, String> createQuery(String message) {

        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("views", parseSearchParam(message, "views"));
        searchParams.put("author", parseSearchParam(message, "author"));
        searchParams.put("answers", parseSearchParam(message, "answers"));
        searchParams.put("tags", parseSearchExactly(message, true));
        searchParams.put("exactSearch", parseSearchExactly(message, false));
        searchParams.put("textSearch", message
                .replaceAll("(\"[^\"]+\")|(\\[[^\\]\\[]+\\])|([\\]\\[])|(answers:[\\s]?\\d+)|(author:[\\s]?\\d+)|(views:[\\s]?\\d+)", "")
                .replaceAll("\\s{2,}", " ")
                .trim());

        String query = "select question.id as question_id, " +
                "question.title as question_title," +
                "u.fullName as question_authorName," +
                "u.id as question_authorId, " +
                "u.imageLink as question_authorImage," +
                "question.description as question_description," +
                "question.viewCount as question_viewCount," +
                "(select count(a.question.id) from Answer a where a.question.id=question_id) as question_countAnswer," +
                "(select count(v.question.id) from VoteQuestion v where v.question.id=question_id) as question_countValuable," +
                "question.persistDateTime as question_persistDateTime," +
                "question.lastUpdateDateTime as question_lastUpdateDateTime, " +
                "tag.id as tag_id,tag.name as tag_name " +
                "from Question question  " +
                "INNER JOIN  question.user u " +
                "join question.tags tag " +
                "where u.id >= :userId " +
                "and question.viewCount >= :views " +
                "and (select count(a.question.id) from Answer a where a.question.id=question_id) >= :answers " +
                "and question.description like :exactly ";
        
        String[] tags = searchParams.get("tags").split(" ");
        String tagsQuery = "and (";
        for (String tag : tags) {
            tagsQuery += "tag.name like '%" + tag + "%' or ";
        }
        query += tagsQuery.substring(0, tagsQuery.length() - 4) + ")";

        String[] words = searchParams.get("textSearch").split(" ");
        String wordsQuery = " and (";
        for (String word : words) {
            wordsQuery += "question.description like '%" + word + "%' or ";
        }
        query += wordsQuery.substring(0, wordsQuery.length() - 4) + ")";
        searchParams.put("query", query);

        return searchParams;
    }

    private String parseSearchExactly(String message, boolean isTag) {
        StringBuilder exactly = new StringBuilder();
        while (message.matches("(.*\".+\".*)|(.*\\[.+\\].*)")) {
            int firstIndex = message.indexOf(isTag ? "[" : "\"");
            int secondIndex = message.indexOf(isTag ? "]" : "\"", firstIndex + 1) + 1;
            if (firstIndex < 0 || secondIndex < firstIndex) {
                break;
            }
            String tmp = message.substring(firstIndex, secondIndex);
            message = message.replace(tmp, "");
            exactly.append(tmp.substring(1, tmp.length() - 1)).append(" ");
        }
        return exactly.toString().trim();
    }

    private String parseSearchParam(String message, String param) {
        if (message.matches(".*" + param + ":\\d+.*")) {
            int index = message.indexOf(param + ":");
            String results = message.substring(index, index + param.length() + 1);

            for (int i = index + param.length() + 1; i < message.length(); i++) {
                char c = message.charAt(i);
                if (Character.isDigit(c)) {
                    results += c;
                } else {
                    break;
                }
            }
            if (results.length() > param.length() + 1) {
                return results.substring(param.length() + 1);
            }
        }
        return "";
    }
}
