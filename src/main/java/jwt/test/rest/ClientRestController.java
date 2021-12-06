package jwt.test.rest;

import jwt.test.dto.TextDTO;
import jwt.test.model.text.Text;
import jwt.test.model.user.roles.Admin;
import jwt.test.service.user.impl.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/client_panel/{clientId}")
public class ClientRestController {
    private final ClientService clientService;

    @Autowired
    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("create_text")
    public ResponseEntity<Text> createText(@PathVariable UUID clientId, @RequestBody Text text) {
        return new ResponseEntity<>(clientService.saveText(clientId, text), HttpStatus.CREATED);
    }

    @GetMapping("texts")
    public ResponseEntity<List<Text>> getAllTexts(@PathVariable UUID clientId) {
        return new ResponseEntity<>(clientService.findAllTexts(clientId), HttpStatus.OK);
    }

    @GetMapping("texts/{textId}")
    public ResponseEntity<Text> getTextById(@PathVariable UUID clientId, @PathVariable UUID textId) {
        return new ResponseEntity<>(clientService.findTextById(clientId, textId), HttpStatus.OK);
    }

    @PutMapping("texts")
    public ResponseEntity<Text> updateText(@PathVariable UUID clientId, @RequestBody Text text) {
        return new ResponseEntity<>(clientService.updateText(clientId, text), HttpStatus.OK);
    }

}
