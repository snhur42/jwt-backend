package jwt.test.repository.text;

import jwt.test.model.text.Text;
import jwt.test.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends EntityRepository<Text> {
}
