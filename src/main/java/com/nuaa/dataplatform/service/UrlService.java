package com.nuaa.dataplatform.service;

import com.nuaa.dataplatform.dao.UrlDAO;
import com.nuaa.dataplatform.entity.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired
    private UrlDAO urlDAO;

    public Url getUrlByName(String urlName) {
        return urlDAO.selectByUrlName(urlName);
    }

    public Url addUrl(Url url)
    {
        urlDAO.addUrl(url);
        return url;
    }

    public int deleteUrlByName(String urlName) {
        return urlDAO.deleteByUrlName(urlName);
    }

}
