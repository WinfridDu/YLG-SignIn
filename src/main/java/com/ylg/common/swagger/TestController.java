package com.ylg.common.swagger;

import com.ylg.common.controller.BaseController;
import com.ylg.common.entity.PageResult;
import com.ylg.common.entity.Result;
import com.ylg.common.entity.ResultCode;
import com.ylg.domain.system.User;
import com.ylg.domain.system.response.UserResult;
import com.ylg.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("用户数据管理")
//1.解决跨域
@CrossOrigin
//2.声明restContoller
@RestController
//3.设置父路径
@RequestMapping(value="/test")
public class TestController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Result save(@RequestBody User user) {
        //1.设置保存的企业id
        user.setOrganizationId(organizationId);
        user.setOrganizationName(organizationName);
        //2.调用service完成保存企业
        userService.save(user);
        //3.构造返回结果
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询用户列表
     */
    @ApiOperation("查询用户列表")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Result findAll(int page, int size, @RequestParam Map map) {
        //1.获取当前的企业id
        map.put("organizationId", organizationId);
        //2.完成查询
        List userList = userService.findAll(map);
        return new Result(ResultCode.SUCCESS, userList);
    }

    /**
     * 根据ID查询user
     */
    @ApiOperation("根据ID查询user")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        // 添加 roleIds (用户已经具有的角色id数组)
        User user = userService.findById(id);
        UserResult userResult = new UserResult(user);
        return new Result(ResultCode.SUCCESS, userResult);
    }

    /**
     * 修改User
     */
    @ApiOperation("修改User")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody User user) {
        //1.设置修改的部门id
        user.setId(id);
        //2.调用service更新
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     */
    @ApiOperation("根据id删除")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, name = "API-USER-DELETE")
    public Result delete(@PathVariable(value = "id") String id) {
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}