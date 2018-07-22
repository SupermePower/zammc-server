package com.smart.program.service.reserve;

import com.smart.program.request.reserve.UserReserveRequest;

public interface ReserveService {

    /**
     * 用户预约
     *
     * @param request
     * @throws Exception
     */
    void userReserve(UserReserveRequest request) throws Exception;
}
