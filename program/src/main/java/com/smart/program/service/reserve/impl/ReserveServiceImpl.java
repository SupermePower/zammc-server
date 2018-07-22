package com.smart.program.service.reserve.impl;

import com.smart.program.domain.reserve.ReserveEntity;
import com.smart.program.idwork.IdWorker;
import com.smart.program.repository.reserve.ReserveRepository;
import com.smart.program.request.reserve.UserReserveRequest;
import com.smart.program.service.reserve.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private IdWorker idWorker;

    /**
     * 用户预约
     *
     * @param request
     * @throws Exception
     */
    public void userReserve(UserReserveRequest request) throws Exception {
        ReserveEntity reserveEntity = new ReserveEntity();
        reserveEntity.setReserveId(idWorker.nextId());
        reserveEntity.setRealName(request.getRealName());
        reserveEntity.setMobile(request.getMobile());
        reserveEntity.setReserveNumbers(request.getReserveNumbers());
        reserveEntity.setReserveTime(request.getCurrentDate() + " " + request.getCurrentTime());
        reserveEntity.setSummary(request.getSummary());
        reserveEntity.setDataStatus((byte) 1);
        reserveRepository.saveAndFlush(reserveEntity);
    }
}
