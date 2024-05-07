package de.joachimsohn.web.authentication.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public final class LoginFormDto {

    private String username;
    private String password;

}
