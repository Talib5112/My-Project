package com.travelbnb.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;
@Data
public class LoginDto {
    private String username;
    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false,length = 100)
    private String password;
}

