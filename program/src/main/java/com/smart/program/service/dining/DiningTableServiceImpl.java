package com.smart.program.service.dining;

import com.smart.program.domain.dining.DiningTableEntity;
import com.smart.program.repository.dining.DiningTableRepository;
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

    /**
     * 修改餐位状态
     * @param id 餐位主键
     * @throws Exception
     */
    @Override
    public void updateStatus(Long id) throws Exception {
        DiningTableEntity diningTableEntity = diningTableRepository.findById(id).get();
        if (diningTableEntity != null) {
            diningTableEntity.setTableStatus((byte) 0);
            diningTableRepository.saveAndFlush(diningTableEntity);
        }
    }
}
