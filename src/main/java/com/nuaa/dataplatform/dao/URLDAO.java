package com.nuaa.dataplatform.dao;

import com.nuaa.dataplatform.entity.URL;
import com.nuaa.dataplatform.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface URLDAO {
    String TABLE_NAME = "url";
    String INSERT_FIELDS = " name, address, initAuthorId, lastAuthorId ";
    String SELECT_FIELDS = " id, name, address, initAuthorId, lastAuthorId ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name},#{address},#{initAuthorId},#{lastAuthorId})"})
    int addURL(URL url);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    URL selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    URL selectByName(String name);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where address=#{address}"})
    URL selectByAddress(String address);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where initAuthorId=#{initAuthorId}"})
    URL selectByInitAuthorId(int initAuthorId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where lastAuthorId=#{lastAuthorId}"})
    URL selectByLastAuthorId(int lastAuthorId);

    @Update({"update ", TABLE_NAME, " set name=#{name},address=#{address},initAuthorId=#{initAuthorId}," +
            "lastAuthorId=#{lastAuthorId} where id=#{id}"})
    void updateURL(URL url);

    @Update({"update ", TABLE_NAME, " set name=#{name} where id=#{id}"})
    void updateNameIdById(int id, String name);

    @Update({"update ", TABLE_NAME, " set address=#{address} where id=#{id}"})
    void updateAddressIdById(int id, String address);

    @Update({"update ", TABLE_NAME, " set initAuthorId=#{initAuthorId} where id=#{id}"})
    void updateInitAuthorIdById(int id, String initAuthorId);

    @Update({"update ", TABLE_NAME, " set lastAuthorId=#{lastAuthorId} where id=#{id}"})
    void updateLastAuthorityById(int id, String lastAuthorId);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
