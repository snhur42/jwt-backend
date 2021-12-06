package jwt.test.service.text;

import jwt.test.model.text.Text;
import jwt.test.repository.text.TextRepository;
import jwt.test.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Qualifier("photoService")
public class TextService extends EntityServiceImpl<Text, TextRepository> {
    public TextService(TextRepository textRepository) {
        super(textRepository);
    }
}




