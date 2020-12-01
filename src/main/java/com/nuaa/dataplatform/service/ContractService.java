package com.nuaa.dataplatform.service;

import com.nuaa.dataplatform.dao.ContractDAO;
import com.nuaa.dataplatform.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractDAO contractDAO;

    public Contract getContractById(int id) {
        return contractDAO.selectById(id);
    }

    public List<Contract> getContractsByUrl(String url) {
        return contractDAO.selectByUrl(url);
    }

    public Contract addContract(String url, String contractNo, String contractName, String projectNo, String projectName,
                                String purchaser, String purchaserTelNo, String supplier, String supplierTelNo,
                                String subjectName, float subjectUnitPrice, float contractValue, Date announceDate)
    {
        Contract contract = new Contract(url, contractNo, contractName, projectNo, projectName, purchaser, purchaserTelNo,
                supplier, supplierTelNo, subjectName, subjectUnitPrice, contractValue, announceDate);
        contractDAO.addContract(contract);
        return contract;
    }

    public void deleteContract(int id) {
        contractDAO.deleteById(id);
    }

    public void updateContract(Contract contract) {
        contractDAO.updateContractById(contract.getId(), contract.getUrl(), contract.getContractNo(), contract.getContractName(),
                contract.getProjectNo(), contract.getProjectName(), contract.getPurchaser(), contract.getPurchaserTelNo(),
                contract.getSupplier(), contract.getSupplierTelNo(), contract.getSubjectName(), contract.getSubjectUnitPrice(),
                contract.getContractValue(), contract.getAnnounceDate());
    }

}
