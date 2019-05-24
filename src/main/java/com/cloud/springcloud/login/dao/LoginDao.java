package com.cloud.springcloud.login.dao;

import com.cloud.springcloud.login.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDao extends JpaRepository<Login, Integer>, JpaSpecificationExecutor<Integer> {


    @Query(value = " SELECT  *  FROM   tb_login_user  WHERE  l_Id = ?1 AND  str_token = ?2", nativeQuery = true)
    Object queryByIdAndToken(long lId, String token);

    @Query(value = " SELECT  *  FROM   tb_login_user  WHERE  str_Email = ?1  ", nativeQuery = true)
    Object existsByEmail(String str_Email);

    @Query(value = " SELECT  a.l_Id ,a.str_Password ,a.str_Name ,a.str_Token ,a.n_Phone ,a.str_Email ,a.str_Role  FROM   tb_login_user a WHERE  a.str_Email = ?1 AND  a.str_Password = ?2", nativeQuery = true)
    Login queryLoginBylIdAndPw(String str_Email, String str_Password);


}
