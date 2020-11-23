package com.nuaa.dataplatform.service;

import com.nuaa.dataplatform.dao.WebdataDAO;
import com.nuaa.dataplatform.entity.Webdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebdataService {

    @Autowired
    private WebdataDAO webdataDAO;

    public Webdata getContract(int id) {
        return webdataDAO.selectById(id);
    }

    public void updateWebdata(Webdata webdata) {
        webdataDAO.updateWebdata(webdata);
    }
}
