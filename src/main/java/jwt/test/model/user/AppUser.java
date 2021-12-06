package jwt.test.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AppUser extends AppUserSecurity {
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;
    @Column(name = "phone", nullable = false, columnDefinition = "TEXT")
    private String phone;
    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;


    @Override
    public String getUsername() {
        return getEmail();
    }
}
