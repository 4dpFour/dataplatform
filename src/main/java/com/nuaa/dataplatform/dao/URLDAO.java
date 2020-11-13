package com.nuaa.dataplatform.dao;

import com.nuaa.dataplatform.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface URLDAO {
    String TABLE_NAME = "url";
    String INSERT_FIELDS = " name, address, initAuthorId, lastAuthorId ";
    String SELECT_FIELDS = " id, username, password, authority ";
}
