package com.nuaa.dataplatform.controller;

import com.nuaa.dataplatform.entity.Contract;
import com.nuaa.dataplatform.service.ContractService;
import com.nuaa.dataplatform.util.HostHolder;
import com.nuaa.dataplatform.util.Result;
import com.nuaa.dataplatform.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private HostHolder hostHolder;

    @GetMapping("/crawl")
    public Result crawlContracts() {
        try {
            int increment = contractService.crawl(hostHolder.getUser().getUrlsArray());
            if (increment > 0) {
                HashMap<String, Integer> resultMap = new HashMap<>();
                resultMap.put("increment", increment);
                return Result.success(resultMap);
            } else {
                return Result.failure(ResultCode.NOT_FOUND, "没爬到任何数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public Result listContracts(@RequestParam(name = "query", required = false) String query) {
        try {
            List<Contract> contracts;
            if (query == null || query.length() == 0) {
                contracts = contractService.getContractsByUrls(hostHolder.getUser().getUrlsArray());
            } else {
                contracts = contractService.dimSelect(query, hostHolder.getUser().getUrlsArray());
            }
            if (contracts != null && contracts.size() > 0) {
                return Result.success(contracts);
            } else {
                return Result.failure(ResultCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public Result getContractById(@PathVariable int id) {
        try {
            Contract contract = contractService.getContractById(id);
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
    public Result addContract(@RequestBody Map<String, String> requestMap) {
        try {
            Contract contract = new Contract(requestMap);
            if (contract.allFieldEmpty()) {
                return Result.failure(ResultCode.FORBIDDEN, "请至少填入一个字段");
            }
            return Result.success(contractService.addContract(contract));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public Result updateContract(@PathVariable("id") int id,
                                 @RequestBody Map<String, String> requestMap) {
        try {
            Contract contract = new Contract(requestMap);
            if (contract.allFieldEmpty()) {
                return Result.failure(ResultCode.FORBIDDEN, "未输入更新项");
            }
            contractService.updateContract(id, contract);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteContract(@PathVariable("id") int id) {
        try {
            contractService.deleteContractById(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }
}