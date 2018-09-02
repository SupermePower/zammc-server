package com.smart.program.service.dining;

import com.smart.program.common.ErrorConstant;
import com.smart.program.domain.dining.DiningTableEntity;
import com.smart.program.domain.restaurant.RestaurantEntity;
import com.smart.program.repository.dining.DiningTableRepository;
import com.smart.program.response.ResponseVO;
import com.smart.program.service.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 餐位业务实现
 * @author: liying.fu
 * @Date: 2018/8/14 下午10:24
 */
@Service
public class DiningTableServiceImpl implements DiningTableService {

    @Autowired
    private DiningTableRepository diningTableRepository;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * 修改餐位状态
     * @param id 餐位主键
     * @throws Exception
     */
    @Override
    public ResponseVO updateStatus(Long id) throws Exception {
        ResponseVO responseVO = new ResponseVO();
        RestaurantEntity restaurantEntity = restaurantService.queryRestaurant();
        if (restaurantEntity.getStatus() == 0) {
            return responseVO.setResult(ErrorConstant.RESTAURANT_REST_ERROR, ErrorConstant.RESTAURANT_REST_ERROR_MSG);
        }
        DiningTableEntity diningTableEntity = diningTableRepository.findById(id).get();
        if (diningTableEntity != null) {
            diningTableEntity.setTableStatus((byte) 0);
            diningTableRepository.saveAndFlush(diningTableEntity);
        }
        return responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG);
    }
}
