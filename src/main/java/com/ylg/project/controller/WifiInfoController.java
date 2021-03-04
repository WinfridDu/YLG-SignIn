package com.ylg.project.controller;

import com.ylg.common.controller.BaseController;
import com.ylg.common.entity.Result;
import com.ylg.common.entity.ResultCode;
import com.ylg.project.domain.WifiInfo;
import com.ylg.project.service.WifiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value="/wifi/info")
public class WifiInfoController extends BaseController {
    @Autowired
    private WifiInfoService wifiInfoService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result save(@RequestBody WifiInfo wifiInfo) {
        wifiInfo.setOrganizationId(organizationId);
        try{
            wifiInfoService.save(wifiInfo);
        }catch (DataIntegrityViolationException e){
            return new Result(ResultCode.WIFI_EXIST);
        }
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody WifiInfo wifiInfo) {
        wifiInfo.setId(id);
        wifiInfo.setOrganizationId(organizationId);
        wifiInfoService.update(wifiInfo);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result find() {
        WifiInfo wifiInfo = wifiInfoService.findByOrganizationId(organizationId);
        return new Result(ResultCode.SUCCESS, wifiInfo);
    }
}
