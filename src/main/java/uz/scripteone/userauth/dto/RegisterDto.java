package uz.scripteone.userauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstname;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    private String lastname;

    @Email(message = "Notog'ri")
    @NotNull
    private String email;

    @NotNull
    private LocalDate dataOfBirth;

    @NotNull
    private String preferredBranch;

    @NotNull
    private String englishLevel;

    @NotNull
    private String region;

    private String address;

    private String parentFullName;

    private String parentMobile;


}
