package com.javamentor.qa.platform.models.entity.question;

import com.javamentor.qa.platform.exception.ConstrainException;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 6264105282197120461L;
    @Id
    @GeneratedValue(generator = "Tag_seq")
    private Long id;

    @NotNull
    @Column
    private String name;


    @Column
    private String description;

    @CreationTimestamp
    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime persistDateTime;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Question> questions;

    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    @PreUpdate
    private void preUpdateFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (this.description.isEmpty()) {
            throw new ConstrainException("Поле description не должно быть пустым");
        }
        if (this.name.isEmpty()) {
            throw new ConstrainException("Поле name не должно быть пустым");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) &&
                Objects.equals(name, tag.name) &&
                Objects.equals(description, tag.description) &&
                Objects.equals(persistDateTime, tag.persistDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, persistDateTime);
    }


}
