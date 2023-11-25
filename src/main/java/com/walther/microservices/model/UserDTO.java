package com.walther.microservices.model;

import com.walther.microservices.validators.CIF;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> {
    private Integer id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String lastName;
    @NotNull
    @Min(value = 18)
    private Integer edad;
    @Email
    @NotBlank
    private String email;
    @AssertTrue
    @NotNull
    private boolean active;
    @Past
    private LocalDate birthDay;
    @CIF
    private String cif;
    private String title;
    private String body;

}
