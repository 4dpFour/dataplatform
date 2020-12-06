package com.nuaa.dataplatform.dao;

import com.nuaa.dataplatform.entity.Contract;
import com.nuaa.dataplatform.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface ContractDAO {
    String TABLE_NAME = "contract";
    String INSERT_FIELDS = " url, contractNo, contractName, projectNo, projectName, purchaser, purchaserTelNo, " +
            "supplier, supplierTelNo, subjectName, subjectUnitPrice, contractValue, announceDate ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    int addContract(Contract contract);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where (url like CONCAT('%', #{str}, '%')) or " +
            "(contractNo like CONCAT('%', #{str}, '%')) or (contractName like CONCAT('%', #{str}, '%')) or " +
            "(projectNo like CONCAT('%', #{str}, '%')) or (projectName like CONCAT('%', #{str}, '%')) or " +
            "(purchaser like CONCAT('%', #{str}, '%')) or (purchaserTelNo like CONCAT('%', #{str}, '%')) or " +
            "(supplier like CONCAT('%', #{str}, '%')) or (supplierTelNo like CONCAT('%', #{str}, '%')) or " +
            "(subjectName like CONCAT('%', #{str}, '%')) or (subjectUnitPrice like CONCAT('%', #{str}, '%')) or " +
            "(contractValue like CONCAT('%', #{str}, '%')) or (announceDate like CONCAT('%', #{str}, '%')) order by id desc"})
    List<Contract> dimSelect(String str);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Contract selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where url=#{url} order by id desc"})
    List<Contract> selectByUrl(String url);

    List<Contract> selectByUrls(List<String> urls);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where url=#{url} order by id desc limit 1"})
    Contract selectNewestByUrl(String url);

    void updateContract(Contract contract);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    int deleteById(int id);

    int deleteByIds(List<Integer> ids);

}
