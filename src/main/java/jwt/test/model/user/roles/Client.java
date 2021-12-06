package jwt.test.model.user.roles;

import jwt.test.model.text.Text;
import jwt.test.model.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Client")
@Table(name = "client",
        uniqueConstraints = {
                @UniqueConstraint(name = "client_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "client_phone_unique", columnNames = "phone")
        }
)
public class Client extends AppUser {
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "texts_client_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "texts_client_id_fk"
            )
    )
    private List<Text> texts;
}
