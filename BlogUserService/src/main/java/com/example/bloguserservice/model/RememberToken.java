package com.example.bloguserservice.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class RememberToken implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String token;
    @Column(name = "expired_at")
    private Date expiredAt;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
