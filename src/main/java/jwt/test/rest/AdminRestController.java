package jwt.test.rest;

import jwt.test.dto.UserDTO;
import jwt.test.model.text.Text;
import jwt.test.model.user.roles.Admin;
import jwt.test.model.user.roles.Client;
import jwt.test.service.user.impl.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("api/admin_panel")
public class AdminRestController {
    private final AdminService adminService;

    @Autowired
    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("create_admin")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        return new ResponseEntity<>(adminService.save(admin), HttpStatus.CREATED);
    }

    @GetMapping("admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return new ResponseEntity<>(adminService.findAll(), HttpStatus.OK);
    }

    @GetMapping("admins/{adminId}")
    public ResponseEntity<Admin> getAdminById(@PathVariable UUID adminId) {
        return new ResponseEntity<>(adminService.findById(adminId), HttpStatus.OK);
    }

    @PutMapping("admins")
    public ResponseEntity<Admin> updateAdmin(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(adminService.update(userDTO), HttpStatus.OK);
    }

    @PostMapping("create_client")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        return new ResponseEntity<>(adminService.saveClient(client), HttpStatus.CREATED);
    }

    @GetMapping("clients")
    public ResponseEntity<List<Client>> getAllClients() {
        return new ResponseEntity<>(adminService.findAllClients(), HttpStatus.OK);
    }

    @GetMapping("clients/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable UUID clientId) {
        return new ResponseEntity<>(adminService.findClientById(clientId), HttpStatus.OK);
    }

    @PutMapping("clients")
    public ResponseEntity<Client> updateClient(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(adminService.updateClient(userDTO), HttpStatus.OK);
    }

    @DeleteMapping("clients/{clientId}")
    public ResponseEntity<?> deleteClientById(@PathVariable UUID clientId) {
        adminService.deleteClientById(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("texts")
    public ResponseEntity<List<Text>> getAllTexts() {
        return new ResponseEntity<>(adminService.findAllTexts(), HttpStatus.OK);
    }

    @GetMapping("texts/{textID}")
    public ResponseEntity<List<Text>> getAllTextByClientId(@PathVariable UUID textID) {
        return new ResponseEntity<>(adminService.findAllTextsByClientId(textID), HttpStatus.OK);
    }
}
