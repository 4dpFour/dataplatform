package com.nuaa.dataplatform.service;

import com.nuaa.dataplatform.dao.ContractDAO;
import com.nuaa.dataplatform.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContractService {

    @Autowired
    private ContractDAO contractDAO;

    public Contract getContract(int id) {
        return contractDAO.selectById(id);
    }

    public Contract addContract(String contractNo, String contractName, String projectNo, String projectName,
                                String purchaser, String purchaserTelNo, String supplier, String supplierTelNo,
                                String subjectName, float subjectUnitPrice, float contractValue, Date announceDate)
    {
        Contract contract = new Contract(contractNo, contractName, projectNo, projectName, purchaser, purchaserTelNo,
                supplier, supplierTelNo, subjectName, subjectUnitPrice, contractValue, announceDate);
        contractDAO.addContract(contract);
        return contract;
    }

    public void deleteContract(int id) {
        contractDAO.deleteById(id);
    }

    public void updateUser(Contract contract) {
        contractDAO.updateContract(contract);
    }

}
