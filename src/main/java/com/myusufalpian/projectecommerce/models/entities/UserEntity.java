package com.myusufalpian.projectecommerce.models.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "tb_pengguna", schema = "public")
public class UserEntity implements Serializable {

    public UserEntity(String username2) {
        this.username = username2;
    }

    private static final long serialVersionUID = 1L;

    @Id
    private String username;

    @Column(name = "nama_pengguna", length = 100)
    private String nama;

    @Column(name = "email_pengguna", length = 60)
    private String email;

    @Column(name = "alamat_pengguna", length = 300)
    private String alamat;

    @Column(name = "password_pengguna", length = 64)
    private String password;

    @Column(name = "role_pengguna", length = 15)
    private String role;

    @Column(name = "is_active")
    private Boolean isActive;
}
