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

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where url=#{url}"})
    Contract selectByDimUrl(String url);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where contractNo=#{contractNo}"})
    Contract selectByDimContractNo(String contractNo);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where contractName=#{contractName}"})
    Contract selectByDimContractName(String contractName);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where projectNo=#{projectNo}"})
    Contract selectByDimProjectNo(String projectNo);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where projectName=#{projectName}"})
    Contract selectByDimProjectName(String projectName);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where purchaser=#{purchaser}"})
    Contract selectByDimPurchaser(String purchaser);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where purchaserTelNo=#{purchaserTelNo}"})
    Contract selectByDimPurchaserTelNo(String purchaserTelNo);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where supplier=#{supplier}"})
    Contract selectByDimSupplier(String supplier);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where supplierTelNo=#{supplierTelNo}"})
    Contract selectByDimSupplierTelNo(String supplierTelNo);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where subjectName=#{subjectName}"})
    Contract selectByDimSubjectName(String subjectName);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where subjectUnitPrice=#{subjectUnitPrice}"})
    Contract selectByDimSubjectUnitPrice(String subjectUnitPrice);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where contractValue=#{contractValue}"})
    Contract selectByDimContractValue(String contractValue);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where announceDate=#{announceDate}"})
    Contract selectByDimAnnounceDate(String announceDate);

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
