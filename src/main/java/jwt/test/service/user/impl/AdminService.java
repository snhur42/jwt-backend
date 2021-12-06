package jwt.test.service.user.impl;

import jwt.test.dto.UserDTO;
import jwt.test.model.text.Text;
import jwt.test.model.user.roles.Admin;
import jwt.test.model.user.roles.Client;
import jwt.test.repository.user.AdminRepository;
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
@Qualifier("adminService")
public class AdminService extends UserServiceImpl<Admin, AdminRepository> {
    private final ClientService clientService;
    private final TextService textService;

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        ClientService clientService,
                        TextService textService,
                        PasswordEncoder passwordEncoder) {
        super(adminRepository, passwordEncoder);
        this.textService = textService;
        this.clientService = clientService;
    }


    public Client saveClient(Client client) {
        return clientService.save(client);
    }

    public List<Client> findAllClients() {
        return clientService.findAll();
    }

    public Client findClientById(UUID clientId) {
        return clientService.findById(clientId);
    }

    public Client updateClient(UserDTO userDTO) {
        return clientService.update(userDTO);
    }

    public void deleteClientById(UUID clientId) {
        clientService.deleteById(clientId);
    }


    public List<Text> findAllTexts() {
        return textService.findAll();
    }

    public List<Text> findAllTextsByClientId(UUID clientId) {
        return this.findClientById(clientId).getTexts();
    }

}