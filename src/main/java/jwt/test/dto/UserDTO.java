package jwt.test.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
