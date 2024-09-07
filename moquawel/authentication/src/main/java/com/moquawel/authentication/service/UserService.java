package com.moquawel.authentication.service;


import com.moquawel.authentication.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//
//    public List<UserDto> getStudents(int grade) {
//        var students = userRepository.findByRoleAndGrade(ERole.STUDENT, grade);
//        var users = new ArrayList<UserDto>();
//
//        students.forEach(
//                user -> users.add(buildUserDto(user))
//        );
//
//        return users;
//    }
//
//
//    public UserDto buildUserDto(User user) {
//        return UserDto
//                .builder()
//                .idUser(user.getIdUser())
//                .email(user.getEmail())
//                .role(user.getRole().name())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .identifier(user.getIdentifier())
//                .idGrade(user.getIdgrade())
//                .build();
//    }
}
