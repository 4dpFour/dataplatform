package com.nuaa.dataplatform.dao;

import com.nuaa.dataplatform.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " username, password, authority, careUrls ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{username},#{password},#{authority})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where username=#{username}"})
    User selectByUsername(String username);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where authority=#{authority}"})
    List<User> selectByAuthority(int id);

    void updateUserById(@Param("id") int id, @Param("password") String password, @Param("authority") int authority, @Param("careUrls") String careUrls);

    @Update({"update ", TABLE_NAME, " set careUrls=#{careUrls} where id=#{id}"})
    void updateCareUrlsById(int id, String careUrls);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
