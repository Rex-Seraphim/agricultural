package com.cl.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.cl.entity.OrdersEntity;
import com.cl.entity.view.OrdersView;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 商品订单
 *
 * @author
 * @email
 * @date 2024-12-09 09:01:51
 */
public interface OrdersDao extends BaseMapper<OrdersEntity> {

    List<OrdersView> selectListView(@Param("ew") Wrapper<OrdersEntity> wrapper);

    List<OrdersView> selectListView(Pagination page, @Param("ew") Wrapper<OrdersEntity> wrapper);

    OrdersView selectView(@Param("ew") Wrapper<OrdersEntity> wrapper);


}
