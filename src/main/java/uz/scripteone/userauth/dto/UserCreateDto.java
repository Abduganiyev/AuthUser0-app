package uz.scripteone.userauth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Set<Long> roleIds;
}
