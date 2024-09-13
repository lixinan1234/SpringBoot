package com.itheima.mp.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/9/12 22:48
 */
@Data
@ApiModel(description = "分页结果")
public class PageDTO<T> {
    @ApiModelProperty("总条数")
    private Long total;
    @ApiModelProperty("总页数")
    private Long pages;
    @ApiModelProperty("集合")
    private List<T> list;


    public static <PO,VO> PageDTO<VO> of(Page<PO> p, Function<PO,VO> convertor){
        PageDTO<VO> dto = new PageDTO<>();
        //1总条数
        dto.setTotal(p.getTotal());
        //2.总页数
        dto.setPages(p.getPages());
        //3.当前页数据
        List<PO> records = p.getRecords();
        if(CollUtil.isEmpty(records)){
            dto.setList(Collections.emptyList());
            return dto;
        }
        //4.拷贝user的Vo
        dto.setList(records.stream().map(convertor).collect(Collectors.toList()));
        //5.返回
        return dto;
    }
}
