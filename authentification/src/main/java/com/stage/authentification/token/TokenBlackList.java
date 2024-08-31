package com.stage.authentification.token;


import com.stage.authentification.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "token_blacklist")
public class TokenBlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_blacklist_SEQ")
    @SequenceGenerator(name = "token_blacklist_SEQ", sequenceName = "token_blacklist_SEQ", allocationSize = 1)
    @Column(name = "idToken")
    private Integer idToken;

    private String jti;


    @Column(
            name = "created_at",
            insertable = false
    )
    private Timestamp createdAt;

    @Column(name = "expires_at")
    private Timestamp expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    private User user;

}
