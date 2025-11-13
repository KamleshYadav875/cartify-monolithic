package com.ecom.cartify.controller;

import com.ecom.cartify.dto.UserRequest;
import com.ecom.cartify.dto.UserResponse;
import com.ecom.cartify.entity.User;
import com.ecom.cartify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUsers(@PathVariable Long id){
//        User user  = userService.fetchUsers(id);
//        if(ObjectUtils.isEmpty(user)){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(user);

        return userService.fetchUsers(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUsers(@RequestBody UserRequest request){
        userService.addUser(request);
        return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUsers(@PathVariable Long id, @RequestBody UserRequest request){
        boolean updated = userService.updateUser(id, request);
        if(updated){
            return ResponseEntity.ok("User updated successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
