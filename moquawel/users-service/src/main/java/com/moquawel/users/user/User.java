package com.moquawel.users.user;


import com.moquawel.users.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "user")
public class User  {

    @Id
    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Date created_at;

    private ArrayList<Role> roles;


}
