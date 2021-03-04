package com.ylg.common.interceptor;

import com.ylg.common.annotation.IgnoreAuth;
import com.ylg.common.entity.ResultCode;
import com.ylg.common.exception.CommonException;
import com.ylg.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 *      继承HandlerInterceptorAdapter
 *
 *      preHandle:进入到控制器方法之前执行的内容
 *          boolean：
 *              true：可以继续执行控制器方法
 *              false：拦截
 *      posthandler：执行控制器方法之后执行的内容
 *      afterCompletion：响应结束之前执行的内容
 *
 * 1.简化获取token数据的代码编写
 *      统一的用户权限校验（是否登录）
 * 2.判断用户是否具有当前访问接口的权限
 *
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    /**
     * 简化获取token数据的代码编写（判断是否登录）
     *  1.通过request获取请求token信息
     *  2.从token中解析获取claims
     *  3.将claims绑定到request域中
     */

    @Autowired
    private JwtUtils jwtUtils;

    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
    public static final String LOGIN_TOKEN_KEY = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //支持跨域请求
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,X-Nideshop-Token,X-URL-PATH");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        IgnoreAuth annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }
        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }else{
            //从header中获取token
            String token = request.getHeader(LOGIN_TOKEN_KEY);
            //如果header中不存在token，则从参数中获取token
            if (StringUtils.isEmpty(token)) {
                token = request.getParameter(LOGIN_TOKEN_KEY);
            }

            //token为空
            if (StringUtils.isEmpty(token)) {
                throw new CommonException(ResultCode.LOGINFAIL);
            }

            Claims claims = jwtUtils.parseJwt(token);
            if(claims != null) {
                //设置userId到request里，后续根据userId，获取用户信息
                request.setAttribute(LOGIN_USER_KEY, claims.getId());
                //通过claims获取到当前用户的可访问API权限字符串
                String apis = (String) claims.get("apis");  //api-user-delete,api-user-update
                //通过handler
                HandlerMethod h = (HandlerMethod) handler;
                //获取接口上的reqeustmapping注解
                RequestMapping reqAnnotation = h.getMethodAnnotation(RequestMapping.class);
                //获取当前请求接口中的name属性
                String name = reqAnnotation.name();
                //判断当前用户是否具有响应的请求权限
                if(apis.contains(name)) {
                    request.setAttribute("user_claims",claims);
                    return true;
                }else {
                    throw new CommonException(ResultCode.UNAUTHORISE);
                }
            }
            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }


    }
}
