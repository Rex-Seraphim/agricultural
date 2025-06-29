package com.cl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cl.dao.OrdersDao;
import com.cl.entity.OrdersEntity;
import com.cl.entity.view.OrdersView;
import com.cl.service.OrdersService;
import com.cl.utils.PageUtils;
import com.cl.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ordersService")
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, OrdersEntity> implements OrdersService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OrdersEntity> page = this.selectPage(
                new Query<OrdersEntity>(params).getPage(),
                new EntityWrapper<OrdersEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<OrdersEntity> wrapper) {
        Page<OrdersView> page = new Query<OrdersView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page, wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }

    @Override
    public List<OrdersView> selectListView(Wrapper<OrdersEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public OrdersView selectView(Wrapper<OrdersEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }


}
