package jwt.test.repository;

import jwt.test.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface EntityRepository<T extends AbstractEntity> extends JpaRepository<T, UUID> {

}
