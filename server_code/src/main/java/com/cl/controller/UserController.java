package com.cl.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cl.annotation.IgnoreAuth;
import com.cl.entity.UserEntity;
import com.cl.entity.view.UserView;
import com.cl.service.TokenService;
import com.cl.service.UserService;
import com.cl.utils.MPUtil;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 用户
 * 后端接口
 *
 * @author
 * @email
 * @date 2024-12-09 09:01:50
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @Autowired
    private TokenService tokenService;

    /**
     * 登录
     */
    @IgnoreAuth
    @RequestMapping(value = "/login")
    public R login(String username, String password, String captcha, HttpServletRequest request) {
        UserEntity u = userService.selectOne(new EntityWrapper<UserEntity>().eq("user_account", username));
        if (u == null || !u.getUserPassword().equals(password)) {
            return R.error("账号或密码不正确");
        }
        String token = tokenService.generateToken(u.getId(), username, "user", "user");
        return R.ok().put("token", token);
    }


    /**
     * 注册
     */
    @IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody UserEntity user) {
        //ValidatorUtils.validateEntity(user);
        UserEntity u = userService.selectOne(new EntityWrapper<UserEntity>().eq("user_account", user.getUserAccount()));
        if (u != null) {
            return R.error("注册用户已存在");
        }
        Long uId = new Date().getTime();
        user.setId(uId);
        userService.insert(user);
        return R.ok();
    }


    /**
     * 退出
     */
    @RequestMapping("/logout")
    public R logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return R.ok("退出成功");
    }

    /**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("userId");
        return R.ok().put("data", userService.selectView(new EntityWrapper<UserEntity>().eq("id", id)));
    }

    /**
     * 密码重置
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request) {
        UserEntity u = userService.selectOne(new EntityWrapper<UserEntity>().eq("user_account", username));
        if (u == null) {
            return R.error("账号不存在");
        }
        u.setUserPassword("123456");
        userService.updateById(u);
        return R.ok("密码已重置为：123456");
    }


    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, UserEntity user,
                  HttpServletRequest request) {
        EntityWrapper<UserEntity> ew = new EntityWrapper<UserEntity>();


        PageUtils page = userService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, user), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, UserEntity user,
                  HttpServletRequest request) {
        EntityWrapper<UserEntity> ew = new EntityWrapper<UserEntity>();

        PageUtils page = userService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, user), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(UserEntity user) {
        EntityWrapper<UserEntity> ew = new EntityWrapper<UserEntity>();
        ew.allEq(MPUtil.allEQMapPre(user, "user"));
        return R.ok().put("data", userService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(UserEntity user) {
        EntityWrapper<UserEntity> ew = new EntityWrapper<UserEntity>();
        ew.allEq(MPUtil.allEQMapPre(user, "user"));
        UserView userView = userService.selectView(ew);
        return R.ok("查询用户成功").put("data", userView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        UserEntity user = userService.selectById(id);
        user = userService.selectView(new EntityWrapper<UserEntity>().eq("id", id));
        return R.ok().put("data", user);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        UserEntity user = userService.selectById(id);
        user = userService.selectView(new EntityWrapper<UserEntity>().eq("id", id));
        return R.ok().put("data", user);
    }


    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserEntity user, HttpServletRequest request) {
        if (userService.selectCount(new EntityWrapper<UserEntity>().eq("userAccount", user.getUserAccount())) > 0) {
            return R.error("用户账号已存在");
        }
        user.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        //ValidatorUtils.validateEntity(user);
        UserEntity u = userService.selectOne(new EntityWrapper<UserEntity>().eq("user_account", user.getUserAccount()));
        if (u != null) {
            return R.error("用户已存在");
        }
        user.setId(new Date().getTime());
        userService.insert(user);
        return R.ok();
    }

    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody UserEntity user, HttpServletRequest request) {
        if (userService.selectCount(new EntityWrapper<UserEntity>().eq("userAccount", user.getUserAccount())) > 0) {
            return R.error("用户账号已存在");
        }
        user.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        //ValidatorUtils.validateEntity(user);
        UserEntity u = userService.selectOne(new EntityWrapper<UserEntity>().eq("user_account", user.getUserAccount()));
        if (u != null) {
            return R.error("用户已存在");
        }
        user.setId(new Date().getTime());
        userService.insert(user);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody UserEntity user, HttpServletRequest request) {
        //ValidatorUtils.validateEntity(user);
        userService.updateById(user);//全部更新
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        userService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


}
