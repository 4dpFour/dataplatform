package com.nuaa.dataplatform.dao;

import com.nuaa.dataplatform.entity.URL;
import com.nuaa.dataplatform.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface URLDAO {
    String TABLE_NAME = "url";
    String INSERT_FIELDS = " name, address ";
    String SELECT_FIELDS = " id, name, address ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name},#{address})"})
    int addURL(URL url);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    URL selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "order by id limit #{offset},#{limit}"})
    List<URL> selectByOffset(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    URL selectByName(String name);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where address=#{address}"})
    URL selectByAddress(String address);

    void updateURLById(@Param("id") int id, @Param("name") String name, @Param("address") String address);

    @Update({"update ", TABLE_NAME, " set name=#{name} where id=#{id}"})
    void updateNameById(int id, String name);

    @Update({"update ", TABLE_NAME, " set address=#{address} where id=#{id}"})
    void updateAddressById(int id, String address);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
