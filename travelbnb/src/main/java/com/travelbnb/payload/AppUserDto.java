package com.travelbnb.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AppUserDto {

    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false,length = 100)
    private String password;

    private Long id;

    private String name;

    private String username;

    private String email;
    private String role;
}
