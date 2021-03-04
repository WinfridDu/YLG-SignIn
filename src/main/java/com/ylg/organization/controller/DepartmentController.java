package com.ylg.organization.controller;

import com.ylg.common.annotation.IgnoreAuth;
import com.ylg.common.controller.BaseController;
import com.ylg.common.entity.Result;
import com.ylg.common.entity.ResultCode;
import com.ylg.domain.organization.Department;
import com.ylg.organization.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//1.解决跨域
@CrossOrigin
//2.声明restContoller
@RestController
//3.设置父路径
@RequestMapping(value="/organization")   //  organization/deparment
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 保存
     */
    @RequestMapping(value="/department",method = RequestMethod.POST)
    public Result save(@RequestBody Department department) {
        //1.设置保存的组织id
        /**
         * 组织id：目前使用固定值1，以后会解决
         */
        department.setOrganizationId(organizationId);
        //2.调用service完成保存部门
        try{
            departmentService.save(department);
        }catch (DataIntegrityViolationException e){
            return new Result(ResultCode.DEPARTMENT_EXIT);
        }
        //3.构造返回结果
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询组织的部门列表
     * 指定组织id
     */
    @IgnoreAuth
    @RequestMapping(value="/department/reg",method = RequestMethod.GET)
    public Result findAll(String organizationId) {
        //1.指定组织id
        List<Department> list= departmentService.findAll(organizationId);
        return new Result(ResultCode.SUCCESS,list);
    }

    @RequestMapping(value="/department",method = RequestMethod.GET)
    public Result findAll() {
        List<Department> list= departmentService.findAll(organizationId);
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * 根据ID查询department
     */
    @RequestMapping(value="/department/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value="id") String id) {
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,department);
    }

    /**
     * 修改Department
     */
    @RequestMapping(value="/department/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value="id") String id,@RequestBody Department department) {
        //1.设置修改的部门id
        department.setId(id);
        //2.调用service更新
        try{
            departmentService.update(department);
        }catch (DataIntegrityViolationException e){
            return new Result(ResultCode.DEPARTMENT_EXIT);
        }
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value="/department/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value="id") String id) {
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }


}
