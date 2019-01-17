package org.base.mieuf.project.bussiness.index.controller;

import com.alibaba.fastjson.JSONObject;
import org.base.mieuf.project.bussiness.index.IndexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Date 2019/1/16  10:47
 * @Description TODO
 **/
@Controller
@RequestMapping("/index")
public class IndexController {

    @Resource
    private IndexService indexServiceImpl;

    @RequestMapping("/getAll")
    @ResponseBody
    public String configInfo(){
        return JSONObject.toJSONString(indexServiceImpl.getAllConfig());
    }

}
