package com.kiprenko.springfield.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.time.LocalDate;

@Entity
@Builder
@Getter @Setter
@ToString(exclude = "password")
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String encryptedPassword;
    @Transient
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birth;
    private UserRole role;
    @Version
    private Integer version;
}
