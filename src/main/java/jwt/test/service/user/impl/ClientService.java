package jwt.test.service.user.impl;

import jwt.test.model.text.Text;
import jwt.test.model.user.roles.Client;
import jwt.test.repository.user.ClientRepository;
import jwt.test.service.text.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Qualifier("clientService")
public class ClientService extends UserServiceImpl<Client, ClientRepository> {
    private final TextService textService;

    @Autowired
    public ClientService(ClientRepository clientRepository,
                         TextService textService,
                         PasswordEncoder passwordEncoder) {
        super(clientRepository, passwordEncoder);
        this.textService = textService;
    }

    public Text saveText(UUID clientId, Text text) {
        Client client = this.repository.findById(clientId).get();
        client.getTexts().add(text);
        Text newText = textService.save(text);
        this.repository.save(client);
        return newText;
    }

    public List<Text> findAllTexts(UUID clientId) {
        return repository.findById(clientId).get().getTexts();
    }

    public Text findTextById(UUID clientId, UUID textId) {
        List<Text> texts = this.findAllTexts(clientId);
        boolean isHere = texts.stream().anyMatch(text -> text.getId().equals(textId));
        if (isHere){
            return textService.findById(textId);
        } else {
            throw new RuntimeException("Can`t find text by id: " + textId);
        }
    }
    public Text updateText(UUID clientId, Text text) {
        Text newText = this.findTextById(clientId, text.getId());
        newText.setText(text.getText());
        newText.setChosen(text.isChosen());
        return textService.update(newText);
    }


}