package com.cl.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;
import com.cl.entity.AddressEntity;
import com.cl.entity.view.AddressView;
import com.cl.service.AddressService;
import com.cl.utils.MPUtil;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * 地址
 * 后端接口
 *
 * @author
 * @email
 * @date 2024-12-09 09:01:51
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;


    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, AddressEntity address,
                  HttpServletRequest request) {
        if (!request.getSession().getAttribute("role").toString().equals("管理员")) {
            address.setUserid((Long) request.getSession().getAttribute("userId"));
        }
        EntityWrapper<AddressEntity> ew = new EntityWrapper<AddressEntity>();


        PageUtils page = addressService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, address), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, AddressEntity address,
                  HttpServletRequest request) {
        if (!request.getSession().getAttribute("role").toString().equals("管理员")) {
            address.setUserid((Long) request.getSession().getAttribute("userId"));
        }
        EntityWrapper<AddressEntity> ew = new EntityWrapper<AddressEntity>();

        PageUtils page = addressService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, address), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(AddressEntity address) {
        EntityWrapper<AddressEntity> ew = new EntityWrapper<AddressEntity>();
        ew.allEq(MPUtil.allEQMapPre(address, "address"));
        return R.ok().put("data", addressService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(AddressEntity address) {
        EntityWrapper<AddressEntity> ew = new EntityWrapper<AddressEntity>();
        ew.allEq(MPUtil.allEQMapPre(address, "address"));
        AddressView addressView = addressService.selectView(ew);
        return R.ok("查询地址成功").put("data", addressView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        AddressEntity address = addressService.selectById(id);
        address = addressService.selectView(new EntityWrapper<AddressEntity>().eq("id", id));
        return R.ok().put("data", address);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        AddressEntity address = addressService.selectById(id);
        address = addressService.selectView(new EntityWrapper<AddressEntity>().eq("id", id));
        return R.ok().put("data", address);
    }


    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AddressEntity address, HttpServletRequest request) {
        //ValidatorUtils.validateEntity(address);
        address.setUserid((Long) request.getSession().getAttribute("userId"));
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (address.getIsdefault().equals("是")) {
            addressService.updateForSet("isdefault='否'", new EntityWrapper<AddressEntity>().eq("userid", userId));
        }
        address.setUserid(userId);
        addressService.insert(address);
        return R.ok();
    }

    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody AddressEntity address, HttpServletRequest request) {
        //ValidatorUtils.validateEntity(address);
        address.setUserid((Long) request.getSession().getAttribute("userId"));
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (address.getIsdefault().equals("是")) {
            addressService.updateForSet("isdefault='否'", new EntityWrapper<AddressEntity>().eq("userid", userId));
        }
        address.setUserid(userId);
        addressService.insert(address);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody AddressEntity address, HttpServletRequest request) {
        //ValidatorUtils.validateEntity(address);
        if (address.getIsdefault().equals("是")) {
            addressService.updateForSet("isdefault='否'", new EntityWrapper<AddressEntity>().eq("userid", request.getSession().getAttribute("userId")));
        }
        addressService.updateById(address);//全部更新
        return R.ok();
    }


    /**
     * 获取默认地址
     */
    @RequestMapping("/default")
    public R defaultAddress(HttpServletRequest request) {
        Wrapper<AddressEntity> wrapper = new EntityWrapper<AddressEntity>().eq("isdefault", "是").eq("userid", request.getSession().getAttribute("userId"));
        AddressEntity address = addressService.selectOne(wrapper);
        return R.ok().put("data", address);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        addressService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


}
