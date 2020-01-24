package com.example.springProjekt2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {
    @NotNull
    @Size(min = 8, max = 24)
    private String nick;
    @NotNull
    @Size(min = 8, max = 24)
    private String password;


}
