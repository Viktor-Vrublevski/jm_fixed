package com.javamentor.qa.platform.models.entity.question;

import com.javamentor.qa.platform.exception.ConstrainException;
import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes_on_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteQuestion implements Serializable {

    private static final long serialVersionUID = 6479035497338780810L;

    @Id
    @GeneratedValue(generator = "VoteQuestion_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime localDateTime = LocalDateTime.now();

    private int vote;

    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (vote != 1 && vote != -1) {
            throw new ConstrainException("В сущности VoteQuestion допускается передача значения в поле vote только 1 или -1");
        }
    }
}
