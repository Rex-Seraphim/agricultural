package com.cl.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.cl.entity.AgriculturalproductinformationEntity;
import com.cl.entity.view.AgriculturalproductinformationView;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 农产品信息
 *
 * @author
 * @email
 * @date 2024-12-09 09:01:50
 */
public interface AgriculturalproductinformationDao extends BaseMapper<AgriculturalproductinformationEntity> {

    List<AgriculturalproductinformationView> selectListView(@Param("ew") Wrapper<AgriculturalproductinformationEntity> wrapper);

    List<AgriculturalproductinformationView> selectListView(Pagination page, @Param("ew") Wrapper<AgriculturalproductinformationEntity> wrapper);

    AgriculturalproductinformationView selectView(@Param("ew") Wrapper<AgriculturalproductinformationEntity> wrapper);


}
