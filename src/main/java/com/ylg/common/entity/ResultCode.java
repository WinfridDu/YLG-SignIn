package com.ylg.common.entity;

import org.omg.CORBA.OBJECT_NOT_EXIST;

/**
 * 公共的返回码
 *    返回码code：
 *      成功：10000
 *      失败：10001
 *      未认证：10002
 *      未授权：10003
 *      抛出异常：99999
 */
public enum ResultCode {

    //---系统错误返回码-----
    SUCCESS(true,10000,"操作成功！"),
    FAIL(false,10001,"操作失败"),
    UNAUTHENTICATED(false,10002,"认证未通过"),
    UNAUTHORISE(false,10003,"权限不足"),
    LOGINFAIL(false,10004,"登陆失败"),
    AUTHENTICATING(true,10005,"身份待审核"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),
    TOKEN_FAIL(false,10006,"token失效，请重新登录"),
    FIRST_LOGIN(true,10006,"首次登录"),
    REPEAT_MOBILE(false,10007,"手机号重复"),

    TIME_ERROR(false,20001,"结束时间应在起始时间之后"),
    REPEAT_TIME(false,20002,"不能设置两个起始时间或结束时间相同的时间段"),
    NOT_IN_SIGN_TIME(false,20003,"当前时间不能报班"),
    UP_TO_MAX(false,20004,"该班报班人数达到上限，报班失败"),
    REPEAT_SAME(false,20005,"你已经报过该班"),
    OBJECT_NOT_EXIST(false,20006,"不存在"),
    NOT_IN_DUTY_TIME(false,20007,"当前时间不能签到"),
    NEVER_SIGN_UP(false,20008,"你未报过该班或你已请假"),
    REPEAT_SIGN_IN(false,20009,"你已签过到"),
    NOT_IN_REVOKE_TIME(false,20010,"当前时间不能撤销报班"),

    IN_DUTY(true,30001,"值班中"),
    NOT_IN_DUTY(true,30002,"不在值班中"),
    NOT_IN_SIGNOUT_TIME(false,30003,"值班时间未结束"),

    WIFI_EXIST(false,40001,"wifi已设置，不能设置多个wifi"),
    DEPARTMENT_EXIT(false,40002,"已存在同名部门"),
    LACK_OF_WIFIINFO(false,50001,"未设置wifi信息"),
    LACK_OF_SIGNRULE(false,50002,"未设置签到签退规则"),
    LACK_OF_DUTYTIME(false,50003,"未设置值班时间"),
    LACK_OF_SIGNUP_LIMIT(false,50004,"未设置报班时间和人数限制");

    //---用户操作返回码----
    //---中心操作返回码----
    //---权限操作返回码----
    //---其他操作返回码----

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    ResultCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

}
