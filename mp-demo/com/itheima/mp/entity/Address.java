package com.itheima.mp.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (address)实体类
 *
 * @author kancy
 * @since 2024-09-12 09:08:26
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("address")
public class Address extends Model<Address> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
	private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 县/区
     */
    private String town;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 详细地址
     */
    private String street;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 是否是默认 1默认 0否
     */
    private Integer isDefault;
    /**
     * 备注
     */
    private String notes;
    /**
     * 逻辑删除
     */
    private Integer deleted;

}