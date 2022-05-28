package com.example.bloguserservice.api.v1.DTO;

import com.example.bloguserservice.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = -953297098295050686L;

    private Long id;
    private String fullName;
    private String email;
    private List<Role> roles;
    private String profile;
    private RememberTokenDTO token;

    @JsonProperty("user_url")
    private String userUrl;
}
