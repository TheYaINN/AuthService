package de.joachimsohn.web.authentication.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public final class RegisterFormDto {

    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;

}
