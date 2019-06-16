package com.ligen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by ligen on 2018/5/8.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {

    private Integer id;
    private String name; //用户名
    private String password;//用户密码
    private Date dtCreate;//时间
    private Date dtUpdate;//时间

}
