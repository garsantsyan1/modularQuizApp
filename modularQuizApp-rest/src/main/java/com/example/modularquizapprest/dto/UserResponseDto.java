package com.example.modularquizapprest.dto;

import com.example.modularquizappcommon.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private int id;
    private String name;
    private String surname;
    private String email;
    private UserType type;

}
