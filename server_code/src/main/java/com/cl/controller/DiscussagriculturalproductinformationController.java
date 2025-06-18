package com.cl.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cl.annotation.IgnoreAuth;
import com.cl.entity.DiscussagriculturalproductinformationEntity;
import com.cl.entity.view.DiscussagriculturalproductinformationView;
import com.cl.service.DiscussagriculturalproductinformationService;
import com.cl.utils.MPUtil;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * agriculturalproductinformation评论表
 * 后端接口
 */
@RestController
@RequestMapping("/discussagriculturalproductinformation")
public class DiscussagriculturalproductinformationController {
    @Autowired
    private DiscussagriculturalproductinformationService discussagriculturalproductinformationService;


    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, DiscussagriculturalproductinformationEntity discussagriculturalproductinformation,
                  HttpServletRequest request) {
        EntityWrapper<DiscussagriculturalproductinformationEntity> ew = new EntityWrapper<DiscussagriculturalproductinformationEntity>();


        PageUtils page = discussagriculturalproductinformationService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussagriculturalproductinformation), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, DiscussagriculturalproductinformationEntity discussagriculturalproductinformation,
                  HttpServletRequest request) {
        EntityWrapper<DiscussagriculturalproductinformationEntity> ew = new EntityWrapper<DiscussagriculturalproductinformationEntity>();

        PageUtils page = discussagriculturalproductinformationService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussagriculturalproductinformation), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(DiscussagriculturalproductinformationEntity discussagriculturalproductinformation) {
        EntityWrapper<DiscussagriculturalproductinformationEntity> ew = new EntityWrapper<DiscussagriculturalproductinformationEntity>();
        ew.allEq(MPUtil.allEQMapPre(discussagriculturalproductinformation, "discussagriculturalproductinformation"));
        return R.ok().put("data", discussagriculturalproductinformationService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DiscussagriculturalproductinformationEntity discussagriculturalproductinformation) {
        EntityWrapper<DiscussagriculturalproductinformationEntity> ew = new EntityWrapper<DiscussagriculturalproductinformationEntity>();
        ew.allEq(MPUtil.allEQMapPre(discussagriculturalproductinformation, "discussagriculturalproductinformation"));
        DiscussagriculturalproductinformationView discussagriculturalproductinformationView = discussagriculturalproductinformationService.selectView(ew);
        return R.ok("查询agriculturalproductinformation评论表成功").put("data", discussagriculturalproductinformationView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        DiscussagriculturalproductinformationEntity discussagriculturalproductinformation = discussagriculturalproductinformationService.selectById(id);
        discussagriculturalproductinformation = discussagriculturalproductinformationService.selectView(new EntityWrapper<DiscussagriculturalproductinformationEntity>().eq("id", id));
        return R.ok().put("data", discussagriculturalproductinformation);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        DiscussagriculturalproductinformationEntity discussagriculturalproductinformation = discussagriculturalproductinformationService.selectById(id);
        discussagriculturalproductinformation = discussagriculturalproductinformationService.selectView(new EntityWrapper<DiscussagriculturalproductinformationEntity>().eq("id", id));
        return R.ok().put("data", discussagriculturalproductinformation);
    }


    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DiscussagriculturalproductinformationEntity discussagriculturalproductinformation, HttpServletRequest request) {
        //ValidatorUtils.validateEntity(discussagriculturalproductinformation);
        discussagriculturalproductinformationService.insert(discussagriculturalproductinformation);
        return R.ok();
    }

    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody DiscussagriculturalproductinformationEntity discussagriculturalproductinformation, HttpServletRequest request) {
        //ValidatorUtils.validateEntity(discussagriculturalproductinformation);
        discussagriculturalproductinformationService.insert(discussagriculturalproductinformation);
        return R.ok();
    }

    /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username) {
        DiscussagriculturalproductinformationEntity discussagriculturalproductinformation = discussagriculturalproductinformationService.selectOne(new EntityWrapper<DiscussagriculturalproductinformationEntity>().eq("", username));
        return R.ok().put("data", discussagriculturalproductinformation);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody DiscussagriculturalproductinformationEntity discussagriculturalproductinformation, HttpServletRequest request) {
        //ValidatorUtils.validateEntity(discussagriculturalproductinformation);
        discussagriculturalproductinformationService.updateById(discussagriculturalproductinformation);//全部更新
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        discussagriculturalproductinformationService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 前端智能排序
     */
    @IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, DiscussagriculturalproductinformationEntity discussagriculturalproductinformation, HttpServletRequest request, String pre) {
        EntityWrapper<DiscussagriculturalproductinformationEntity> ew = new EntityWrapper<DiscussagriculturalproductinformationEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            String newKey = entry.getKey();
            if (pre.endsWith(".")) {
                newMap.put(pre + newKey, entry.getValue());
            } else if (StringUtils.isEmpty(pre)) {
                newMap.put(newKey, entry.getValue());
            } else {
                newMap.put(pre + "." + newKey, entry.getValue());
            }
        }
        params.put("sort", "clicktime");
        params.put("order", "desc");
        PageUtils page = discussagriculturalproductinformationService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussagriculturalproductinformation), params), params));
        return R.ok().put("data", page);
    }


}
