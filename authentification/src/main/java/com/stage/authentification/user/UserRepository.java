package com.stage.authentification.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);


    @Query("""
            select u from User u where u.idgrade= :idgrade and u.role= :role
            """)
    List<User> findByRoleAndGrade(Role role,int idgrade);
}
