package com.lwm.smarthome.pojo;

import lombok.Data;

import java.util.Map;

@Data
public class UserResp  {

    private Long id;

    private String account;

    private String userName;

    private String password;

    private Map role;
}
