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

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where purchaser=#{purchaser}"})
    List<Contract> selectByPurchaser(String purchaser);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where supplier=#{supplier}"})
    List<Contract> selectBySupplier(String supplier);


    void updateContractById(@Param("id") int id, @Param("url") String url, @Param("contractNo") String contractNo, @Param("contractName") String contractName, @Param("projectNo") String projectNo,
                            @Param("projectName") String projectName, @Param("purchaser") String purchaser, @Param("purchaserTelNo") String purchaserTelNo,
                            @Param("supplier") String supplier, @Param("supplierTelNo") String supplierTelNo, @Param("subjectName") String subjectName,
                            @Param("subjectUnitPrice") float subjectUnitPrice, @Param("contractValue") float contractValue, @Param("announceDate") Date announceDate);

    @Update({"update ", TABLE_NAME, " set contractNo=#{contractNo} where id=#{id}"})
    void updateContractNoById(int id, String contractNo);

    @Update({"update ", TABLE_NAME, " set contractName=#{contractName} where id=#{id}"})
    void updateContractNameById(int id, String contractName);

    @Update({"update ", TABLE_NAME, " set projectNo=#{projectNo} where id=#{id}"})
    void updateProjectNoById(int id, String projectNo);

    @Update({"update ", TABLE_NAME, " set projectName=#{projectName} where id=#{id}"})
    void updateProjectNameById(int id, String projectName);

    @Update({"update ", TABLE_NAME, " set purchaser=#{purchaser} where id=#{id}"})
    void updatePurchaserById(int id, String purchaser);

    @Update({"update ", TABLE_NAME, " set purchaserTelNo=#{purchaserTelNo} where id=#{id}"})
    void updatePurchaserTelNoById(int id, String purchaserTelNo);

    @Update({"update ", TABLE_NAME, " set supplier=#{supplier} where id=#{id}"})
    void updateSupplierById(int id, String supplier);

    @Update({"update ", TABLE_NAME, " set supplierTelNo=#{supplierTelNo} where id=#{id}"})
    void updateSupplierTelNoById(int id, String supplierTelNo);

    @Update({"update ", TABLE_NAME, " set subjectName=#{subjectName} where id=#{id}"})
    void updateSubjectNameById(int id, String subjectName);

    @Update({"update ", TABLE_NAME, " set subjectUnitPrice=#{subjectUnitPrice} where id=#{id}"})
    void updateSubjectUnitPriceById(int id, float subjectUnitPrice);

    @Update({"update ", TABLE_NAME, " set contractValue=#{contractValue} where id=#{id}"})
    void updateContractValueById(int id, float contractValue);

    @Update({"update ", TABLE_NAME, " set announceDate=#{announceDate} where id=#{id}"})
    void updateAnnounceDateById(int id, float announceDate);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);

    @Delete({"delete from ", TABLE_NAME, " where contractNo=#{contractNo}"})
    void deleteByContractNo(String contractNo);
}
