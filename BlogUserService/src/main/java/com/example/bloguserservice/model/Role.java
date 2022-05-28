package com.example.bloguserservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Role implements Serializable {
    private static final long serialVersionUID = -2731425678149216053L;

    @Id
    @GeneratedValue
    private long id;
    private String roleId;
    private String role;
}
