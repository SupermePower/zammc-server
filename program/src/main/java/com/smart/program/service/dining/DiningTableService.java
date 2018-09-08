package com.smart.program.service.dining;

import com.smart.program.response.ResponseVO;

/**
 * @description 餐位业务接口
 * @author: liying.fu
 * @Date: 2018/8/14 下午10:24
 */
public interface DiningTableService {

    /**
     * 修改餐位状态
     * @param id 餐位主键
     * @throws Exception
     */
    ResponseVO<String> updateStatus(Long id) throws Exception;
}
