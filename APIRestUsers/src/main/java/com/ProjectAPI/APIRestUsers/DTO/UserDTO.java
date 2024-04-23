package com.ProjectAPI.APIRestUsers.DTO;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String name;
    private String surname;
    private String username;
    private String email;

}
