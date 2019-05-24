package com.cloud.springcloud.login.service;

import com.cloud.springcloud.core.CoreResponse;
import com.cloud.springcloud.core.CoreResponseCode;
import com.cloud.springcloud.login.dao.LoginDao;
import com.cloud.springcloud.login.model.Login;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.AbstractDocument;

@Service
public class LoginService {

    Logger  logger = Logger.getLogger(this.getClass());
    @Autowired
    private LoginDao  loginDao ;


    /**
     * 判断该用户是否存在
     * @param str_Email
     * @return
     */
    public  Object  existLoginUser(String str_Email){
        return loginDao.existsByEmail(str_Email);
    }

    /**
     * 插入用户
     * @param login
     * @return
     */
    public  boolean insertLoginUser(Login login){
        boolean  flag ;

        try {
            loginDao.save(login);
            flag =true;
        }catch (Exception e){
            flag = false ;
            logger.error("insertLoginUser 接口异常：",e);
        }
        return flag;
    }

    /**
     * 删除用户
     * @param lId
     * @return
     */
    public  boolean  deleteLoginUser(long  lId){


        return  false ;
    }

    /**
     * 根据lId + strtoken 查询该用户
     * @param lId
     * @param strtoken
     * @return
     */
    public  Login  queryByIdAndToken(long lId ,String  strtoken){

        Login login   ;
        login = (Login)this.loginDao.queryByIdAndToken(lId, strtoken) ;
        return  login;
    }

    /**
     * 更新用户信息
     * @return
     */
    public  boolean updateLoginUser(Login login){

        return  false ;
    }


    /**
     * 登录
     * @return
     */
    public CoreResponse login(String str_Email  ,String  pwd){
        CoreResponse<Login> coreResponse = new CoreResponse();

        if(pwd.equals("") || pwd==null ){
            coreResponse.setCode(CoreResponseCode.CODE_BUSINESS_ERROR_9);
            coreResponse.setMessage("请输入密码！");
            return  coreResponse ;
        }
        if(str_Email.equals("") || str_Email==null ){
            coreResponse.setCode(CoreResponseCode.CODE_BUSINESS_ERROR_9);
            coreResponse.setMessage("请输入邮箱！");
            return  coreResponse ;
        }
        if(this.existLoginUser(str_Email)!= null){//判断该用户是否存在
            Login login =this.loginDao.queryLoginBylIdAndPw(str_Email ,pwd) ;
            if(login !=null){
                coreResponse.setCode(CoreResponseCode.CODE_OK);
                coreResponse.setMessage("登录成功！");
                coreResponse.setT(login);
            }else{
                coreResponse.setCode(CoreResponseCode.CODE_BUSINESS_ERROR_9);
                coreResponse.setMessage("登录失败，密码错误！");
            }
        }else {
            coreResponse.setCode(CoreResponseCode.CODE_BUSINESS_ERROR_9);
            coreResponse.setMessage("该用户不存在！");
        }

        return  coreResponse ;
    }




}
