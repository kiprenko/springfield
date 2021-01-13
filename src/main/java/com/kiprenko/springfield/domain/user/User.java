package com.kiprenko.springfield.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Builder
@Getter @Setter
@ToString(exclude = {"password", "encryptedPassword"})
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Column(unique = true)
    @Size(min = 3, max = 30)
    private String username;
    @NotBlank
    @Size(min = 3, max = 68)
    private String encryptedPassword;
    @Transient
    private String password;
    @NotBlank
    @Size(min = 3, max = 30)
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 30)
    private String lastName;
    @NotNull
    @PastOrPresent
    private LocalDate birth;
    @NotNull
    private UserRole role;
    @Version
    private Integer version;
}
