package com.guava.E_HOSTELS.communications.notifications.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {

    @Id
    private String nickName;
    private String fullName;
    private Status status;
}
