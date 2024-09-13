package com.itheima.mp.domain.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/9/12 22:34
 */
@Data
@ApiModel(description = "分页查询实体")
public class PageQuery {
    @ApiModelProperty("页码")
    private Integer pageNo = 1;//设置默认值为一，防止空指针
    @ApiModelProperty("页码")
    private Integer pageSize = 5;
    @ApiModelProperty("排序字段")
    private String sortBy;
    @ApiModelProperty("是否升序")
    private Boolean isAsc = true;

    public <T> Page<T> toMpPage(OrderItem ... items){
        //1.分页条件
        Page<T> page = Page.of(pageNo,pageSize);
        //2.排序条件
        if(StrUtil.isNotBlank(sortBy)){//判断是否为空
            //不为空
            page.addOrder(new OrderItem().setColumn(sortBy).setAsc(isAsc));
        }else {
            //为空，默认排序
            page.addOrder(items);
        }
        return page;
    }

    //默认排序
    public <T> Page<T> toMpPage(String defaultSortBy,Boolean defaultAsc){
        return toMpPage(new OrderItem().setColumn(defaultSortBy).setAsc(defaultAsc));
    }
    public <T> Page<T> toMpPageDefaultSortByCreateTime(){
        return toMpPage(new OrderItem().setColumn("create_time").setAsc(false));
    }
    public <T> Page<T> toMpPageDefaultSortByUpdateTime(){
        return toMpPage(new OrderItem().setColumn("update_time").setAsc(false));
    }

}
