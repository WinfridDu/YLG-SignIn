package com.ylg.system.controller;

import com.ylg.common.annotation.IgnoreAuth;
import com.ylg.common.annotation.LoginUser;
import com.ylg.common.controller.BaseController;
import com.ylg.common.entity.Result;
import com.ylg.common.entity.ResultCode;
import com.ylg.common.entity.WXSessionModel;
import com.ylg.common.utils.*;
import com.ylg.domain.system.Permission;
import com.ylg.domain.system.Role;
import com.ylg.domain.system.User;
import com.ylg.organization.service.DepartmentService;
import com.ylg.organization.service.OrganizationService;
import com.ylg.system.service.PermissionService;
import com.ylg.system.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@CrossOrigin
@RestController
@RequestMapping("/auth")
@ConfigurationProperties("weixin.config")
public class AuthController extends BaseController {

    private String appid;
    private String secret;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PermissionService permissionService;

    @IgnoreAuth
    @RequestMapping(value = "/login_by_weixin", method = RequestMethod.POST)
    public Result loginByWeixin(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", appid);
        param.put("secret", secret);
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
        String wxResult = HttpClientUtil.doGet(url, param);
        WXSessionModel model = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);

        if(null==model || StringUtils.isEmpty(model.getOpenid())){
            return new Result(ResultCode.LOGINFAIL);
        }
        User user = userService.findByOpenid(model.getOpenid());
        if(null == user){
            return new Result(ResultCode.FIRST_LOGIN, model.getOpenid());
        }

        if (user.getEnableState()==0){
            return new Result(ResultCode.AUTHENTICATING);
        }else if(user.getEnableState()==2){
            return new Result(ResultCode.UNAUTHENTICATED);
        }

        //api权限字符串
        StringBuilder sb = new StringBuilder();
        //获取到所有的可访问API权限
        for (Role role : user.getRoles()) {
            for (Permission perm : role.getPermissions()) {
                if(perm.getType() == PermissionConstants.PERMISSION_API) {
                    sb.append(perm.getCode()).append(",");
                }
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("apis",sb.toString());//可访问的api权限字符串
        map.put("organizationId",user.getOrganizationId());
        map.put("organizationName",user.getOrganizationName());

        String token = jwtUtils.createJwt(user.getId(), user.getName(), map);
        if(StringUtils.isEmpty(token)){
            return new Result(ResultCode.LOGINFAIL);
        }

        return new Result(ResultCode.SUCCESS,token);
    }

    @IgnoreAuth
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result loginByWeixin(String openid,String level, @RequestBody User userInfo) {
        User user = new User();
        String id = idWorker.nextId()+"";
        user.setId(id);
        user.setMobile(userInfo.getMobile());
        user.setName(userInfo.getName());
        user.setOpenid(openid);
        user.setEnableState(0);
        user.setCreateTime(new Date());
        user.setInServiceStatus(1);
        user.setLevel(level);
        if(null!=userInfo.getDepartmentId()){
            user.setDepartmentId(userInfo.getDepartmentId());
            user.setDepartmentName(departmentService.findById(userInfo.getDepartmentId()).getName());
        }
        if(null!=userInfo.getOrganizationId()){
            user.setOrganizationId(userInfo.getOrganizationId());
            user.setOrganizationName(organizationService.findById(userInfo.getOrganizationId()).getName());
        }if(null!=userInfo.getOrganizationName()){
            user.setOrganizationName(userInfo.getOrganizationName());
        }
        try {
            userService.save(user);
        }catch(DataIntegrityViolationException e) {
            return new Result(ResultCode.REPEAT_MOBILE);
        }

        return new Result(ResultCode.AUTHENTICATING);
    }

    @RequestMapping(value="/profile",method = RequestMethod.POST)
    public Result profile(@LoginUser User user) throws Exception {
//        String userid = claims.getId();
//        //获取用户信息
//        User user = userService.findById(userid);
//        //根据不同的用户级别获取用户权限
//
//        ProfileResult result = null;
//
//        if("user".equals(user.getLevel())) {
//            result = new ProfileResult(user);
//        }else {
//            Map map = new HashMap();
//            if("coAdmin".equals(user.getLevel())) {
//                map.put("enVisible","1");
//            }
//            List<Permission> list = permissionService.findAll(map);
//            result = new ProfileResult(user,list);
//        }
//        return new Result(ResultCode.SUCCESS,result);
        return new Result(ResultCode.SUCCESS,user.getLevel());
    }
}
