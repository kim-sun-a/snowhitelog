package com.snowhitelog.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Signup {
    private String account;
    private String name;
    private String password;
    private String email;
}
