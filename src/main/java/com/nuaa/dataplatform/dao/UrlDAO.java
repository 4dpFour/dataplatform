package com.nuaa.dataplatform.dao;

import com.nuaa.dataplatform.entity.Url;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UrlDAO {
    String TABLE_NAME = "url";
    String INSERT_FIELDS = " urlName, seedPage, detailPage, className ";
    String SELECT_FIELDS = INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{urlName},#{seedPage},#{detailPage},#{className})"})
    int addUrl(Url url);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where urlName=#{urlName}"})
    Url selectByUrlName(String urlName);

    @Delete({"delete from ", TABLE_NAME, " where urlName=#{urlName}"})
    int deleteByUrlName(String urlName);
}
