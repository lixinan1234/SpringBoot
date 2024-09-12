package com.itheima.mp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import jdk.jfr.Enabled;
import lombok.Getter;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/9/12 16:51
 */

@Getter
public enum UserStatus {
    NORMAL(1,"正常"),
    FROZEN(2,"冻结"),
    ;
    @EnumValue
    private final int value;
    @JsonValue
    private final String desc;


    UserStatus(int value,String desc) {
        this.value = value;
        this.desc = desc;
    }
}
