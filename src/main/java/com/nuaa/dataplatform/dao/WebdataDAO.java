package com.nuaa.dataplatform.dao;

import com.nuaa.dataplatform.entity.Contract;
import com.nuaa.dataplatform.entity.User;
import com.nuaa.dataplatform.entity.Webdata;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WebdataDAO {
    String TABLE_NAME = "contract";
    String INSERT_FIELDS = " urlId, getDate, contractId ";
    String SELECT_FIELDS = " id " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{urlId},#{getDate},#{contractId})"})
    int addWebdata(Webdata webdata);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Webdata selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where urlId=#{urlId}"})
    List<Webdata> selectByUrlId(String urlId);

    @Update({"update ", TABLE_NAME, " set urlId=#{urlId},getDate=#{getDate},contractId=#{contractId} where id=#{id}"})
    void updateWebdata(Webdata webdata);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
