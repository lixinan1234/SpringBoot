package com.anli.mapper;

import com.anli.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/6/30 11:25
 */
@Mapper
public interface DeptMapper {

    //查询全部部门数据
    @Select("select * from dept")
    List<Dept> list();


    //根据ID删除部门
    @Delete("delete from dept where id = #{id} ")
    void deleteById(Integer id);


    //新增部门信息
    @Insert("insert into dept(name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);




    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);



    @Select("select * from dept where id = #{id}")
    Dept getByID(Integer id);





}
