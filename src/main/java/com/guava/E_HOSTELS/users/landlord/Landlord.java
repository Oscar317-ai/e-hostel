package com.guava.E_HOSTELS.users.landlord;


import com.guava.E_HOSTELS.hostel.building.Building;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "landlord", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName"),
        @UniqueConstraint(columnNames = "email")
})
public class Landlord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long landlordId;

    private String userName;

    private String firstName;
    private String lastName;
    private String password;
    private String photo;

    @Column(unique = true, nullable = false)
    private String email;


    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL)
    private List<Building> buildings;

    public Landlord(String userName, String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email  = email;
        this.userName = userName;
    }

    public Landlord(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
}
