package com.eComApp.user.controller;

import com.eComApp.user.dto.UserRequestDTO;
import com.eComApp.user.dto.userResponseDTO;
import com.eComApp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
/* this will be at class level to specify the base URL for all endpoints in this controller.
For example, if you want all endpoints to be prefixed with /api/users, you can use @RequestMapping("/api/users") at the class level.
*/
@RequestMapping("/api/users")
public class UserController {

    private final UserService UserService;

    //@RequestMapping(value = "/getAllUsers" , method = RequestMethod.GET) //this will be at method level
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<userResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(UserService.getAllUsers() , HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserRequestDTO user) {
        UserService.addUser(user);
        return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<userResponseDTO> getUserById(@PathVariable long id) {
        return UserService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable long id , @RequestBody UserRequestDTO userRequestDTO) {
        boolean isUpdated = UserService.updateUserById(id, userRequestDTO) != null;
        if (isUpdated)
            return ResponseEntity.ok("User updated successfully");
        else
            return ResponseEntity.notFound().build();
    }
}
