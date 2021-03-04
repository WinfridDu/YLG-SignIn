package com.ylg.common.controller;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String organizationId;
    protected String organizationName;
    protected Claims claims;

    //使用jwt方式获取
    @ModelAttribute
    public void setResAnReq(HttpServletRequest request,HttpServletResponse response) {
        this.request = request;
        this.response = response;
        Object obj = request.getAttribute("user_claims");
        if(obj != null) {
            this.claims = (Claims) obj;
            this.organizationId = (String)claims.get("organizationId");
            this.organizationName = (String)claims.get("organizationName");
        }
    }

    //使用shiro获取
//    @ModelAttribute
//    public void setResAnReq(HttpServletRequest request,HttpServletResponse response) {
//        this.request = request;
//        this.response = response;
//
//        //获取session中的安全数据
//        Subject subject = SecurityUtils.getSubject();
//        //1.subject获取所有的安全数据集合
//        PrincipalCollection principals = subject.getPrincipals();
//        if(principals != null && !principals.isEmpty()){
//            //2.获取安全数据
//            ProfileResult result = (ProfileResult)principals.getPrimaryPrincipal();
//            this.companyId = result.getCompanyId();
//            this.companyName = result.getCompany();
//        }
//    }

}
