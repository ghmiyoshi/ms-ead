package com.ead.authuser.models;

import com.ead.authuser.dtos.UserRequestDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_users")
public class User extends JsonAbstract {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 20)
    private String cpf;

    @Column
    private String imageUrl;

    @CreationTimestamp
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;

    {
        this.userStatus = UserStatus.ACTIVE;
    }

    public static User newStudent() {
        var user = new User();
        user.userType = UserType.STUDENT;
        return user;
    }

    public void updateUser(final UserRequestDTO userRequest) {
        this.setFullName(userRequest.fullName());
        this.setPhoneNumber(userRequest.phoneNumber());
    }

    public void updatePassword(final UserRequestDTO userRequest) {
        if (this.password.equals(userRequest.oldPassword())) {
            this.setPassword(userRequest.password());
        } else {
            throw new IllegalArgumentException("Mismatched old password");
        }
    }

    public void updateImageUrl(final String imageUrl) {
        this.setImageUrl(imageUrl);
    }

}
