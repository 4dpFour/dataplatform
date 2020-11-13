package com.nuaa.dataplatform.dao;

import com.nuaa.dataplatform.entity.Contract;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContractDAO {
    String TABLE_NAME = "contract";
    String INSERT_FIELDS = " contractNo, contractName, projectNo, projectName, purchaser, purchaserTelNo, supplier, supplierTelNo, subjectName, subjectUnitPrice, contractValue, announceDate ";
    String SELECT_FIELDS = " id " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{contractNo},#{contractName},#{projectNo},#{projectName},#{purchaser},#{purchaserTelNo},#{supplier},#{supplierTelNo},#{subjectName},#{subjectUnitPrice},#{contractValue},#{announceDate})"})
    int addContract(Contract contract);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Contract selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where contractNo=#{contractNo}"})
    Contract selectByContractNo(String contractNo);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where purchaser=#{purchaser}"})
    List<Contract> selectByPurchaser(String purchaser);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where supplier=#{supplier}"})
    List<Contract> selectBySupplier(String supplier);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);

    @Delete({"delete from ", TABLE_NAME, " where contractNo=#{contractNo}"})
    void deleteByContractNo(String contractNo);
}
