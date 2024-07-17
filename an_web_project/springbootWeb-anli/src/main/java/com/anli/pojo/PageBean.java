package com.anli.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/7/5 9:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {
    private Long total;//记录总数
    private List rows;//当前页数数据列表

}
