package com.nuaa.dataplatform.service;

import com.nuaa.dataplatform.dao.URLDAO;
import com.nuaa.dataplatform.entity.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class URLService {

    @Autowired
    private URLDAO urlDAO;

    public URL getURL(int id) {
        return urlDAO.selectById(id);
    }

    public List<URL> getURLList(int offset, int limit) {
        return urlDAO.selectByOffset(offset, limit);
    }

    public URL addURL(String name, String address, int initAuthorId) {
        URL url = new URL(name, address, initAuthorId);
        urlDAO.addURL(url);
        return url;
    }

    public void deleteURL(int id) {
        urlDAO.deleteById(id);
    }

    public void updateURL(int id, String name, String address, int lastAuthorId) {
        urlDAO.updateURLById(id, name, address, lastAuthorId);
    }
}
