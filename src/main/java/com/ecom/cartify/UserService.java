package com.ecom.cartify;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> userList = new ArrayList<>();
    private long ID = 1L;

    public List<User> fetchAllUsers(){
        return userList;
    }

    public Optional<User> fetchUsers(Long id){

        return userList.stream().filter(user -> user.getId().equals(id)).findFirst();

//        for (User user : userList){
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
    }

    public void addUser(User user){
        user.setId(ID++);
        userList.add(user);
    }


    public boolean updateUser(Long id, User user) {

        return userList.stream().filter(u -> u.getId().equals(id))
                .findFirst()
                .map(u -> {
                    u.setFirstName(user.getFirstName());
                    u.setLastName(user.getLastName());
                    return true;
                }).orElse(false);

    }
}
