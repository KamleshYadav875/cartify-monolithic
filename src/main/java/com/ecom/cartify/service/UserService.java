package com.ecom.cartify.service;

import com.ecom.cartify.dto.AddressDTO;
import com.ecom.cartify.dto.UserRequest;
import com.ecom.cartify.dto.UserResponse;
import com.ecom.cartify.entity.Address;
import com.ecom.cartify.repository.UserRepository;
import com.ecom.cartify.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUsers(Long id){
        return userRepository.findById(id).map(this::mapToUserResponse);
//        return userList.stream().filter(user -> user.getId().equals(id)).findFirst();

//        for (User user : userList){
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
    }

    public void addUser(UserRequest request){
        User user = new User();
        updateUserFromRequest(user, request);
        userRepository.save(user);
    }


    public boolean updateUser(Long id, UserRequest request) {

        return userRepository.findById(id)
                .map(u -> {
                    updateUserFromRequest(u, request);
                    userRepository.save(u);
                    return true;
                }).orElse(false);

    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(!ObjectUtils.isEmpty(user.getAddress())){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }

    private void updateUserFromRequest(User user, UserRequest request){
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        if(!ObjectUtils.isEmpty(request.getAddress())){
            Address address = new Address();
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setCountry(request.getAddress().getCountry());
            address.setZipcode(request.getAddress().getZipcode());
            user.setAddress(address);
        }
    }
}
