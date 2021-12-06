package jwt.test.service.user.impl;

import jwt.test.model.user.roles.Admin;
import jwt.test.model.user.roles.Client;
import jwt.test.repository.user.AdminRepository;
import jwt.test.repository.user.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    AdminRepository adminRepository;
    ClientRepository clientRepository;

    @Autowired
    public UserDetailsServiceImpl(AdminRepository adminRepository,
                                  ClientRepository clientRepository) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        Optional<Client> clientOptional = clientRepository.findByEmail(email);

        if(adminOptional.isPresent()){
            return adminOptional.get();
        }else if(clientOptional.isPresent()){
            return clientOptional.get();
        }else {
            throw new UsernameNotFoundException("There is not User with this email: " + email);
        }
    }


    public UserDetails loadUserByUserId(UUID userId) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepository.findById(userId);
        Optional<Client> clientOptional = clientRepository.findById(userId);

        if(adminOptional.isPresent()){
            return adminOptional.get();
        }else if(clientOptional.isPresent()){
            return clientOptional.get();
        }else {
            throw new UsernameNotFoundException("There is not User with this id: " + userId);
        }
    }

}
