package com.ecom.cartify.dto;

import com.ecom.cartify.entity.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDTO address;
}
