package com.nuritech.stock.mystock.user.domain;

import com.nuritech.stock.mystock.user.Authority;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "authId", unique = true)
    private String authId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ElementCollection
    @CollectionTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Authority> authorities = new TreeSet<>();


    @Builder
    public UserEntity(String authId, String password, Authority...authorityList) {
        this.authId = authId;
        this.password = password;
        this.email = authId;
        this.authorities.addAll(Arrays.asList(authorityList));
    }

}