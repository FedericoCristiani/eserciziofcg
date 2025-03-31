package it.fede.eserciziofcg.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    private Integer id;
    @NotBlank(message = "Il nome è obbligatorio")
    private String firstname;
    @NotBlank(message = "Il cognome è obbligatorio")
    private String lastname;
    @Email(message = "L'email deve essere valida")
    private String email;
    private String address;


}
