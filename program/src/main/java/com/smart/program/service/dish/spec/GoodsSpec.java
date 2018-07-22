package com.smart.program.service.dish.spec;

import com.github.wenhao.jpa.Specifications;
import com.smart.program.domain.goods.GoodsCateEntity;
import com.smart.program.domain.goods.GoodsEntity;
import com.smart.program.domain.goods.GoodsPropertyEntity;
import org.springframework.data.jpa.domain.Specification;

public class GoodsSpec {

    public static Specification<GoodsCateEntity> findCate(){
        return Specifications.<GoodsCateEntity>and()
                .eq("status",(byte)0)
                .eq("dataStatus",(byte)0)
                .build();
    }


    public static Specification<GoodsEntity> findGoods(long cateId){
        return Specifications.<GoodsEntity>and()
                .eq("goodsCate",cateId)
                .eq("goodsStatus",(byte)0)
                .eq("dataStatus",(byte)0)
                .build();
    }

    public static Specification<GoodsEntity> findGoodsByIds(Object[] ids){
        return Specifications.<GoodsEntity>and()
                .in("goodsId",ids)
                .eq("goodsStatus",(byte)0)
                .eq("dataStatus",(byte)0)
                .build();
    }

    public static Specification<GoodsPropertyEntity> findType(Long goodsId){
        return Specifications.<GoodsPropertyEntity>and()
                .eq("goodsId",goodsId)
                .eq("propertyStatus",(byte)0)
                .eq("dataStatus",(byte)0)
                .build();
    }
}
