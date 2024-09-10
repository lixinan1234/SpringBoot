package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    //新增分类
    void save(CategoryDTO categoryDTO);

    //分页查询
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    //根据ID删除
    void deleteById(Long id);

    //修改分类
    void update(CategoryDTO categoryDTO);

    //启用禁用分类
    void startOrStop(Integer status, Long id);

    //根据类型查询分类
    List<Category> list(Integer type);
}
