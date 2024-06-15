package com.guava.E_HOSTELS.users.staff;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "staff", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName"),
        @UniqueConstraint(columnNames = "email")
})
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long staffId;

    @Column(unique = true, nullable = false)
    private String userName;

    private String firstName;
    private String lastName;
    private String password;
    private String photo;
    private String position;


    @Column(unique = true, nullable = false)
    private String email;


    public Staff(String userName, String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.userName = userName;
    }

    public Staff(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
