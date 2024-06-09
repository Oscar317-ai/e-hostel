package com.guava.E_HOSTELS.users.director;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "director", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName"),
        @UniqueConstraint(columnNames = "email")
})
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long directorId;

    @Column(unique = true, nullable = false)
    private String userName;

    private String firstName;
    private String lastName;
    private String password;
    private String photo;


    @Column(unique = true, nullable = false)
    private String email;


    public Director(String userName, String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.userName = userName;
    }

    public Director(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}