package com.example.springProjekt2.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserState {
    private boolean admin;
    private boolean logged;
}
