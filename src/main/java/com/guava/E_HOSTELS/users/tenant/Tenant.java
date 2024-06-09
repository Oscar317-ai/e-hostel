package com.guava.E_HOSTELS.users.tenant;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.guava.E_HOSTELS.hostel.house.House;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tenant", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName"),
        @UniqueConstraint(columnNames = "email")
})
@EqualsAndHashCode(exclude = "houses")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;

    private String userName;

    private String firstName;
    private String lastName;
    private String password;
    private int idNumber;

    @Column(unique = true, nullable = false)
    private String email;
    private String photo;

    @Getter
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<House> houses;

    public Tenant(String userName, String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email  = email;
        this.userName = userName;
    }


    public Tenant(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }
}
