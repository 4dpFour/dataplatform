package com.nuaa.dataplatform.dao;

import com.nuaa.dataplatform.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " username, password, authority ";
    String SELECT_FIELDS = " id, username, password, authority ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{username},#{password},#{authority})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where username=#{username}"})
    User selectByUsername(String username);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where authority=#{authority}"})
    List<User> selectByAuthority(int id);

    @Update({"update ", TABLE_NAME, " set username=#{username},password=#{password},authority=#{authority} where id=#{id}"})
    void updateUser(User user);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePasswordById(int id, String password);

    @Update({"update ", TABLE_NAME, " set authority=#{authority} where id=#{id}"})
    void updateAuthorityById(int id, String authority);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
