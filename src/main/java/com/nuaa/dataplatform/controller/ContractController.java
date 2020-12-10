package com.nuaa.dataplatform.controller;

import com.nuaa.dataplatform.entity.Contract;
import com.nuaa.dataplatform.service.ContractService;
import com.nuaa.dataplatform.util.HostHolder;
import com.nuaa.dataplatform.util.Result;
import com.nuaa.dataplatform.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.nuaa.dataplatform.util.ResultCode.*;

@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private HostHolder hostHolder;
    @Value("${crawl.page.start}")
    private int CRAWL_PAGE_START;
    @Value("${crawl.page.end}")
    private int CRAWL_PAGE_END;
    @Value("${crawl.thread}")
    private int CRAWL_THREAD;

    @GetMapping("/crawl")
    public Result crawlContractsDefault(@RequestParam(name = "start", required = false) Integer start,
                                        @RequestParam(name = "end", required = false) Integer end,
                                        @RequestParam(name = "thread", required = false) Integer thread) {
        try {
            if (start == null) {
                start = CRAWL_PAGE_START;
            }
            if (end == null) {
                end = CRAWL_PAGE_END;
            }
            if (thread == null) {
                thread = CRAWL_THREAD;
            }
            if (end < start) {
                return Result.failure(FORBIDDEN, "页码数不合法");
            }
            int increment = contractService.crawl(hostHolder.getUser().getUrlsList(), start, end, thread);
            if (increment > 0) {
                HashMap<String, Integer> resultMap = new HashMap<>();
                resultMap.put("increment", increment);
                return Result.success(resultMap);
            } else {
                return Result.failure(NOT_FOUND, "没爬到任何数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public Result listContracts(@RequestParam(name = "query", required = false) String query) {
        try {
            List<Contract> contracts;
            if (StrUtil.isEmpty(query)) {
                contracts = contractService.getContractsByUrls(hostHolder.getUser().getUrlsList());
            } else {
                contracts = contractService.dimSelect(query, hostHolder.getUser().getUrlsList());
            }
            if (contracts != null && contracts.size() > 0) {
                return Result.success(contracts);
            } else {
                return Result.failure(NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public Result getContractById(@PathVariable int id) {
        try {
            Contract contract = contractService.getContractById(id);
            if (contract != null) {
                return Result.success(contract);
            } else {
                return Result.failure(NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }


    @PostMapping()
    public Result addContract(@RequestBody Map<String, String> requestMap) {
        try {
            String contractNo = requestMap.get("contractNo");
            String contractName = requestMap.get("contractName");
            if (StrUtil.isEmpty(contractNo) || StrUtil.isEmpty(contractName)) {
                return Result.failure(FORBIDDEN, "合同编号与合同名称不能为空");
            }
            if (contractService.getContractByNoAndName(contractNo, contractName) != null) {
                return Result.failure(FORBIDDEN, "已存在相同的合同");
            }
            Contract contract = new Contract(requestMap);
            return Result.success(contractService.addContract(contract));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public Result updateContract(@PathVariable("id") int id,
                                 @RequestBody Map<String, String> requestMap) {
        try {
            String contractNo = requestMap.get("contractNo");
            String contractName = requestMap.get("contractName");
            if (!StrUtil.isEmpty(contractNo) && !StrUtil.isEmpty(contractName) && contractService.getContractByNoAndName(contractNo, contractName) != null) {
                return Result.failure(FORBIDDEN, "已存在相同的合同");
            }
            Contract contract = new Contract(requestMap);
            if (contract.allFieldEmpty()) {
                return Result.failure(FORBIDDEN, "未输入更新项");
            }
            contractService.updateContract(id, contract);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteContract(@PathVariable("id") int id) {
        try {
            if (contractService.deleteContractById(id) == 0) {
                return Result.failure(NOT_FOUND, "该合同不存在");
            } else {
                return Result.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }

    @DeleteMapping()
    public Result deleteContracts(@RequestBody Map<String, ArrayList<Integer>> requestMap) {
        try {
            List<Integer> ids = requestMap.get("ids");
            if (ids == null || ids.size() == 0) {
                return Result.failure(FORBIDDEN, "未输入任何id");
            }
            int success = contractService.deleteContractByIds(ids);
            int failure = ids.size() - success;
            if (success == 0) {
                return Result.failure(NOT_FOUND, "没有合同被删除");
            } else {
                Map<String, Integer> resultMap = new HashMap<>();
                resultMap.put("success", success);
                resultMap.put("failure", failure);
                return Result.success(resultMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(SERVER_ERROR);
        }
    }
}