package jwt.test.model.text;

import jwt.test.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Text")
@Table(name = "text")
public class Text extends AbstractEntity {
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Column(columnDefinition = "BOOLEAN default false")
    private boolean isChosen;
}
