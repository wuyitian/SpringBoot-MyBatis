package com.demo.controller;

import com.demo.common.util.StringUtils;
import com.demo.entity.TableInfo;
import com.demo.service.TableInfoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>这是一个TableInfo表的相关操作的控制层，把所有和该表相关的请求都在这里处理</p>
 *
 * @version 1.0
 * @author:WuYiTian
 * @date: 2018-05-09 17:14
 * @history
 */
@Controller
@RequestMapping(value ="/TableInfo")
@CrossOrigin
public class TableInfoController {

    @Resource
    private TableInfoService tableInfoService;

    /**
     * 新增表信息
     * @param request
     * @param model
     */
    @RequestMapping(value = "/saveTableInfo")
    @ResponseBody
    public int saveTableInfo(HttpServletRequest request, Model model, HttpServletResponse response){
        System.out.println("你请求了：saveTableInfo");
        TableInfo tableInfo=new TableInfo();

        System.out.println(request.getParameter("table_name"));
        tableInfo.setTable_name(request.getParameter("table_name"));
        tableInfo.setTable_code(request.getParameter("table_code"));
        tableInfo.setCreate_user_name(request.getParameter("create_user_name"));
        tableInfo.setCreate_user(request.getParameter("create_user"));
        tableInfo.setCreate_time(new Date());
        tableInfo.setDelete_sign(StringUtils.setStringToInt(request.getParameter("delete_sign")));
        tableInfo.setDept_name(request.getParameter("dept_name"));
        tableInfo.setDept_id(request.getParameter("dept_id"));
        tableInfo.setCompany_name(request.getParameter("company_name"));
        tableInfo.setCompany_id(request.getParameter("company_id"));
        // 引入response并设置响应头，实现跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        return tableInfoService.saveTableInfo(tableInfo);
    }
    /**
     * 更新表信息
     * @param request
     * @param model
     */
    @RequestMapping(value = "/updateTableInfo")
    @ResponseBody
    @CrossOrigin
    public int updateTableInfo(HttpServletRequest request, Model model){
        System.out.println("你请求了：updateTableInfo");
        TableInfo tableInfo=new TableInfo();
        tableInfo.setId(request.getParameter("id"));
        tableInfo.setTable_name(request.getParameter("table_name"));
        tableInfo.setTable_code(request.getParameter("table_code"));
        tableInfo.setUpdate_user_name(request.getParameter("update_user_name"));
        tableInfo.setUpdate_user(request.getParameter("update_user"));
        tableInfo.setUpdate_time(new Date());
        tableInfo.setDelete_sign(StringUtils.setStringToInt(request.getParameter("delete_sign")));
        tableInfo.setDept_name(request.getParameter("dept_name"));
        tableInfo.setDept_id(request.getParameter("dept_id"));
        tableInfo.setCompany_name(request.getParameter("company_name"));
        tableInfo.setCompany_id(request.getParameter("company_id"));

        return tableInfoService.updateTableInfo(tableInfo);
    }


    /**
     * 返回当前所有的分页数据，不控制分页
     * @param request
     * @param model
     */
    @RequestMapping(value = "/getTableInfo")
    @ResponseBody
    public TableInfo  getTableInfo(HttpServletRequest request, Model model){
        System.out.println("你请求了：getTableInfo");
        //判断传入的id是否为空
        if(StringUtils.getObjectValue(request.getParameter("id")))
        {
            return null;
        }
        String tableInfoId=(request.getParameter("id"));
        TableInfo  tableInfo= tableInfoService.getTableInfo(tableInfoId);
        return tableInfo;
    }

    /**
     * 该方法是分页返回数据
     * @param request
     * @param model
     */
    @RequestMapping(value = "/listPageTableInfo")
    @ResponseBody
    public Map listPageTableInfo(HttpServletRequest request, Model model){
        //先这样，做标记
        System.out.println("你请求了：listPageTableInfo");

        // mapLike主要是传递要查询的参数
        Map mapLike=new HashMap();
        mapLike.put("id",request.getParameter("id"));
        mapLike.put("table_name",StringUtils.setQueryLike(request.getParameter("table_name")));

        int pageStart= StringUtils.setStringToInt(request.getParameter("pageStart"));
        int pageEnd= StringUtils.setStringToInt(request.getParameter("pageEnd"));
        mapLike.put("pageStart",pageStart);
        mapLike.put("pageEnd",pageEnd);
        List<TableInfo>  tableInfos= tableInfoService.listPageTableInfo(mapLike);

        //使用这个方法是不传递页码的，就能达到统计的效果
        tableInfoService.getTableInfoCount(mapLike);

        Map map=new HashMap();
        map.put("size",tableInfoService.getTableInfoCount(mapLike));
        map.put("result",tableInfos);

        return map;

    }


    /**
     * 返回当前所有的分页数据，不控制分页
     * @param request
     * @param model
     */
    @RequestMapping(value = "/listTableInfo")
    @ResponseBody

    public Map listTableInfo(HttpServletRequest request, Model model, HttpServletResponse response){
        System.out.println("你请求了：listTableInfo");
        Map map=new HashMap();

        List<TableInfo>  tableInfos= tableInfoService.listTableInfo(map);
        map.put("result",tableInfos);
        // 引入response并设置响应头，实现跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        return map;
    }

    /**
     * 根据条件查询表信息
     * @param request
     * @param model
     */
    @RequestMapping(value = "/queryTableInfo")
    @ResponseBody
    public Map queryTableInfo(HttpServletRequest request, Model model){
        System.out.println("你请求了：queryTableInfo");
        String table_name=(request.getParameter("table_name"));
        Map map=new HashMap();
        List<TableInfo>  tableInfos=tableInfoService.getFuzzyTableInfo(table_name);
        map.put("result",tableInfos);
        map.put("size",tableInfos.size());
        return map;
    }



    /**
     * 删除表信息
     * @param request
     * @param model
     */
    @RequestMapping(value = "/removeTableInfo")
    @ResponseBody
    public int removeTableInfo(HttpServletRequest request, Model model, HttpServletResponse response){
        System.out.println("你请求了：removeTableInfo");


        // 引入response并设置响应头，实现跨域访问
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        //判断传入的id是否为空
        if(StringUtils.getObjectValue(request.getParameter("id")))
        {
            return 0;
        }
        String tableInfoId=(request.getParameter("id"));
        return tableInfoService.removeTableInfo(tableInfoId);
    }
}
