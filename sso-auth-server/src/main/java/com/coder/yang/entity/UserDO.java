package com.coder.yang.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/14 21:28
 * @description ：
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tbl_user")
public class UserDO implements Serializable {
    private Long id;
    private String password;
    private String username;
    private LocalDateTime createTime;
}
