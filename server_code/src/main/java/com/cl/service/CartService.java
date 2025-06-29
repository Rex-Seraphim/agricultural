package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.entity.CartEntity;
import com.cl.entity.view.CartView;
import com.cl.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 购物车
 *
 * @author
 * @email
 * @date 2024-12-09 09:01:50
 */
public interface CartService extends IService<CartEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CartView> selectListView(Wrapper<CartEntity> wrapper);

    CartView selectView(@Param("ew") Wrapper<CartEntity> wrapper);

    PageUtils queryPage(Map<String, Object> params, Wrapper<CartEntity> wrapper);


}

