package com.cl.entity.view;

import com.baomidou.mybatisplus.annotations.TableName;
import com.cl.entity.SupplierEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;


/**
 * 供应商
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 *
 * @author
 * @email
 * @date 2024-12-09 09:01:50
 */
@TableName("supplier")
public class SupplierView extends SupplierEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public SupplierView() {
    }

    public SupplierView(SupplierEntity supplierEntity) {
        try {
            BeanUtils.copyProperties(this, supplierEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
