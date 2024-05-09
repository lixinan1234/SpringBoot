package com.java;


/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/5/8 16:47
 */
    public class Resume {
        String name;
        String gen;
        String birthday;
        String phone;
        String mail;
        String address;
        String school;
        String work;
        String skill;


    public Resume(String name, String gen, String birthday, String phone, String mail, String address, String school, String work, String skill) {
        this.name = name;
        this.gen = gen;
        this.birthday = birthday;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.school = school;
        this.work = work;
        this.skill = skill;
    }

    public Resume() {
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return gen
     */
    public String getGen() {
        return gen;
    }

    /**
     * 设置
     * @param gen
     */
    public void setGen(String gen) {
        this.gen = gen;
    }

    /**
     * 获取
     * @return birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置
     * @param birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取
     * @return mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * 设置
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 获取
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取
     * @return school
     */
    public String getSchool() {
        return school;
    }

    /**
     * 设置
     * @param school
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * 获取
     * @return work
     */
    public String getWork() {
        return work;
    }

    /**
     * 设置
     * @param work
     */
    public void setWork(String work) {
        this.work = work;
    }

    /**
     * 获取
     * @return skill
     */
    public String getSkill() {
        return skill;
    }

    /**
     * 设置
     * @param skill
     */
    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String toString() {
        return "Resume{name = " + name + ", gen = " + gen + ", birthday = " + birthday + ", phone = " + phone + ", mail = " + mail + ", address = " + address + ", school = " + school + ", work = " + work + ", skill = " + skill + "}";
    }

}


