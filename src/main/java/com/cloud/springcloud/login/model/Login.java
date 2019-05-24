package com.cloud.springcloud.login.model;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_login_user")
public class Login {

    @Id
    private  int l_Id ;
    @Column
    private  String str_Name ;
    @Column
    private  String  str_Token ;
    @Column
    private  int n_Phone ;
    @Column
    private  String str_Email ;
    @Column
    private String str_Role ;
    @Column
    private  String str_Password ;

    public Login() {
    }

    public Login(int l_Id, String str_Name, String str_Token, int n_Phone, String str_Email, String str_Role , String str_Password) {
        this.l_Id = l_Id;
        this.str_Name = str_Name;
        this.str_Token = str_Token;
        this.n_Phone = n_Phone;
        this.str_Email = str_Email;
        this.str_Role = str_Role;
        this.str_Password = str_Password ;
    }

    public int getL_Id() {
        return l_Id;
    }

    public void setL_Id(int l_Id) {
        this.l_Id = l_Id;
    }

    public String getStr_Name() {
        return str_Name;
    }

    public void setStr_Name(String str_Name) {
        this.str_Name = str_Name;
    }

    public String getStr_Token() {
        return str_Token;
    }

    public void setStr_Token(String str_Token) {
        this.str_Token = str_Token;
    }

    public int getN_Phone() {
        return n_Phone;
    }

    public void setN_Phone(int n_Phone) {
        this.n_Phone = n_Phone;
    }

    public String getStr_Email() {
        return str_Email;
    }

    public void setStr_Email(String str_Email) {
        this.str_Email = str_Email;
    }

    public String getStr_Role() {
        return str_Role;
    }

    public void setStr_Role(String str_Role) {
        this.str_Role = str_Role;
    }

    public String getStr_Password() {
        return str_Password;
    }

    public void setStr_Password(String str_Password) {
        this.str_Password = str_Password;
    }

    @Override
    public String toString() {
        return "Login{" +
                "l_Id=" + l_Id +
                ", str_Name='" + str_Name + '\'' +
                ", str_Token='" + str_Token + '\'' +
                ", n_Phone=" + n_Phone +
                ", str_Email='" + str_Email + '\'' +
                ", str_Role='" + str_Role + '\'' +
                ", str_Password='" + str_Password + '\'' +
                '}';
    }

    /**
     * 转换JSONObject 对象
     * @return
     */
    public JSONObject toJSONObject() {
        JSONObject  jsonObject = new JSONObject() ;
        jsonObject.put("lId",l_Id);
        jsonObject.put("strName",str_Name);
        jsonObject.put("strToken",str_Token);
        jsonObject.put("nPhone",n_Phone);
        jsonObject.put("strEmail",str_Email);
        jsonObject.put("strRole",str_Role);
        jsonObject.put("strPassword",str_Password);
        return  jsonObject ;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
