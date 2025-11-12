package com.ecom.cartify;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUsers(@PathVariable Long id){
//        User user  = userService.fetchUsers(id);
//        if(ObjectUtils.isEmpty(user)){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(user);

        return userService.fetchUsers(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUsers(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUsers(@PathVariable Long id, @RequestBody User user){
        boolean updated = userService.updateUser(id, user);
        if(updated){
            return ResponseEntity.ok("User updated successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
