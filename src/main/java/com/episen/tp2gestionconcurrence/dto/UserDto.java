package com.episen.tp2gestionconcurrence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private String username;
    private List<String> roles;
}
