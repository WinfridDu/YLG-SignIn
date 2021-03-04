package com.ylg.system.controller;

import com.ylg.common.controller.BaseController;
import com.ylg.common.entity.Result;
import com.ylg.common.entity.ResultCode;
import com.ylg.domain.system.User;
import com.ylg.domain.system.response.UserResult;
import com.ylg.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//1.解决跨域
@CrossOrigin
//2.声明restContoller
@RestController
//3.设置父路径
@RequestMapping(value="/sys")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 分配角色
     */
    @RequestMapping(value = "/user/assignRoles", method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map<String,Object> map) {
        //1.获取被分配的用户id
        String userId = (String) map.get("id");
        //2.获取到角色的id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //3.调用service完成角色分配
        userService.assignRoles(userId,roleIds);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Result save(@RequestBody User user) {
        //1.设置保存的组织id
        user.setOrganizationId(organizationId);
        user.setOrganizationName(organizationName);
        //2.调用service完成保存用户
        userService.save(user);
        //3.构造返回结果
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询用户列表
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Result findAll(@RequestParam Map map) {
        map.put("level","user");
        //1.获取当前的组织id
        map.put("organizationId", organizationId);
        //2.完成查询
        List userList = userService.findAll(map);
        return new Result(ResultCode.SUCCESS, userList);
    }

    @RequestMapping(value = "/coAdmin", method = RequestMethod.GET)
    public Result findAllAdmin(@RequestParam Map map) {
        map.put("level","coAdmin");
        List userList = userService.findAll(map);
        return new Result(ResultCode.SUCCESS, userList);
    }

    /**
     * 根据ID查询user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        // 添加 roleIds (用户已经具有的角色id数组)
        User user = userService.findById(id);
        UserResult userResult = new UserResult(user);
        return new Result(ResultCode.SUCCESS, userResult);
    }

    /**
     * 根据openid查询user
     */
    @RequestMapping(value = "/sign/{openid}", method = RequestMethod.GET)
    public Result findByOpenid(@PathVariable(value = "openid") String openid) {
        User user = userService.findByOpenid(openid);
        if (user!=null && user.getEnableState()!=0 && user.getInServiceStatus()!=2){
            return new Result(ResultCode.SUCCESS, user);
        }
        return new Result(ResultCode.UNAUTHENTICATED);
    }

    /**
     * 修改User
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody User user) {
        //1.设置修改的用户id
        user.setId(id);
        //2.调用service更新
        try {
            userService.update(user);
        }catch (DataIntegrityViolationException e){
            return new Result(ResultCode.REPEAT_MOBILE);
        }
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE /*, name = "API-USER-DELETE"*/)
        public Result delete(@PathVariable(value = "id") String id) {
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}