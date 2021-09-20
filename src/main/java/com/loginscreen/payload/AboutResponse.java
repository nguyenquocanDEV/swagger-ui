package com.loginscreen.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AboutResponse {
    private String username;
    private String password = "bí mật";

    public AboutResponse(String username) {
        this.username = username;
    }
}
