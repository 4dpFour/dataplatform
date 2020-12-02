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

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{url},#{contractNo},#{contractName},#{projectNo},#{projectName},#{purchaser},#{purchaserTelNo},#{supplier},#{supplierTelNo},#{subjectName},#{subjectUnitPrice},#{contractValue},#{announceDate})"})
    int addContract(Contract contract);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Contract selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where contractNo=#{contractNo}"})
    Contract selectByContractNo(String contractNo);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where url=#{url}"})
    List<Contract> selectByUrl(String url);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where url=#{url} order by id desc limit 1"})
    Contract selectNewestByUrl(String url);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where purchaser=#{purchaser}"})
    List<Contract> selectByPurchaser(String purchaser);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where supplier=#{supplier}"})
    List<Contract> selectBySupplier(String supplier);


    void updateContractById(@Param("id") int id, @Param("url") String url, @Param("contractNo") String contractNo, @Param("contractName") String contractName, @Param("projectNo") String projectNo,
                            @Param("projectName") String projectName, @Param("purchaser") String purchaser, @Param("purchaserTelNo") String purchaserTelNo,
                            @Param("supplier") String supplier, @Param("supplierTelNo") String supplierTelNo, @Param("subjectName") String subjectName, @Param("subjectUnitPrice") String subjectUnitPrice,
                            @Param("contractValue") String contractValue, @Param("announceDate") String announceDate);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);

    @Delete({"delete from ", TABLE_NAME, " where contractNo=#{contractNo}"})
    void deleteByContractNo(String contractNo);
}
