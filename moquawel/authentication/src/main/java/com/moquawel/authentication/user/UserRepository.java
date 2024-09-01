package com.moquawel.authentication.user;

import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);


/*    @Query("""
            select u from User u where u.idgrade= :idgrade and u.role= :role
            """)
    List<User> findByRoleAndGrade(ERole role, int idgrade);*/
}
