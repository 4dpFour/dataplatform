package com.nuaa.dataplatform.service;

import com.nuaa.dataplatform.dao.ContractDAO;
import com.nuaa.dataplatform.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    @Autowired
    private ContractDAO contractDAO;

    public Contract getContract(int id) {
        return contractDAO.selectById(id);
    }

    public void updateUser(Contract contract) {
        contractDAO.updateContract(contract);
    }

}
