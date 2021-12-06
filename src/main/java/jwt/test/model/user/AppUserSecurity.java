package jwt.test.model.user;

import jwt.test.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AppUserSecurity extends AbstractEntity implements UserDetails {
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "account_non_expired", columnDefinition = "BOOLEAN")
    private boolean accountNonExpired;
    @Column(name = "account_non_locked", columnDefinition = "BOOLEAN")
    private boolean accountNonLocked;
    @Column(name = "credentials_non_expired", columnDefinition = "BOOLEAN")
    private boolean credentialsNonExpired;
    @Column(name = "enabled", columnDefinition = "BOOLEAN")
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole().getAuthorities();
    }


}
