package com.nuaa.dataplatform.controller;

import com.nuaa.dataplatform.entity.Contract;
import com.nuaa.dataplatform.service.ContractService;
import com.nuaa.dataplatform.util.Result;
import com.nuaa.dataplatform.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping("/{id}")
    public Result getContractNo(@PathVariable int id) {
        try {
            Contract contract = contractService.getContract(id);
            if (contract != null) {
                return Result.success(contract);
            } else {
                return Result.failure(ResultCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @PostMapping()
    public Result addContract(@RequestParam(value = "contractNo") String contractNo,
                              @RequestParam(value = "contractName") String contractName,
                              @RequestParam(value = "projectNo") String projectNo,
                              @RequestParam(value = "projectName") String projectName,
                              @RequestParam(value = "purchaser") String purchaser,
                              @RequestParam(value = "purchaserTelNo") String purchaserTelNo,
                              @RequestParam(value = "supplier") String supplier,
                              @RequestParam(value = "supplierTelNo") String supplierTelNo,
                              @RequestParam(value = "subjectName") String subjectName,
                              @RequestParam(value = "subjectUnitPrice") float subjectUnitPrice,
                              @RequestParam(value = "contractValue") float contractValue
                              /*@RequestParam(value = "announceDate") Date announceDate*/) {
        try {
           if (contractNo == null || contractNo.length() == 0) {
               return Result.failure(ResultCode.FORBIDDEN, "contractNo 为空");
           }
           if (contractName == null || contractName.length() == 0) {
               return Result.failure(ResultCode.FORBIDDEN, "contractName 为空");
           }
           if (projectNo == null || projectNo.length() == 0) {
               return Result.failure(ResultCode.FORBIDDEN, "projectNo 为空");
           }
           if (projectName == null || projectName.length() == 0) {
               return Result.failure(ResultCode.FORBIDDEN, "projectName 为空");
           }
           if (purchaser == null || purchaser.length() == 0) {
               return Result.failure(ResultCode.FORBIDDEN, "purchaser 为空");
           }
           if (purchaserTelNo == null || purchaserTelNo.length() == 0) {
               return Result.failure(ResultCode.FORBIDDEN, "purchaserTelNo 为空");
           }
           if (supplier == null || supplier.length() == 0) {
               return Result.failure(ResultCode.FORBIDDEN, "supplier 为空");
           }
           if (supplierTelNo == null || supplierTelNo.length() == 0) {
               return Result.failure(ResultCode.FORBIDDEN, "supplierTelNo 为空");
           }
           Contract contract = contractService.addContract(contractNo, contractName, projectNo, projectName,
                   purchaser, purchaserTelNo, supplier, supplierTelNo, subjectName, subjectUnitPrice,
                   contractValue, null);
           return Result.success(contract);
        } catch (Exception e) {
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

}