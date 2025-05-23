package com.ivan.prueba.userManagement.entities;

import com.ivan.prueba.userManagement.utilities.EncryptData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = " no puede estar vacio ")
    @Size(min = 3, max = 20, message = " debe tener entre 3 y 20 caracteres")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = " no puede estar vacio")
    @Size(min = 6, max = 200, message = " debe tener longitud minima de 6 caracteres")
    @Column(nullable = false)
    private String password;

    public void setPassword(String password) {
        this.password = EncryptData.encryptPassword(password);
    }
}
