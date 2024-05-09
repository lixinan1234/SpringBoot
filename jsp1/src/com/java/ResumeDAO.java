package com.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/5/8 16:47
 */
public class ResumeDAO {
    boolean insertResume(Resume resume){
        FileWriter fileWriter = null;
        //设置一个文件保存的目录
        File fileDir = new File("C:\\Users\\李新安\\Desktop\\Java代码\\10\\jsp1\\web\\text");
        //文件夹是否存在
        if (!fileDir.exists()){
            //不存在则创建
            fileDir.mkdirs();
        }

        //拼接地址
        String fileAddress = fileDir + "\\" + resume.name + ".txt";
        try {
            //如果文件不存在会自动创建。内容追加到后面
            fileWriter = new FileWriter(new File(fileAddress), StandardCharsets.UTF_8,true);
            //拼接存入内容
            String text = "简历\r\n姓名："+resume.name+"\r\n性别："+resume.gen+"\r\n出生日期："
                    +resume.birthday+"\r\n联系电话："+resume.phone+"\r\n电子邮件："+resume.mail
                    +"\r\n居住地址："+resume.address+"\r\n教育背景："+resume.school+"\r\n工作经验："
                    +resume.work+"\r\n技能特长："+resume.skill+"\r\n\r\n";
            fileWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileWriter != null){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }
}
