package com.nuaa.dataplatform.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

public class Contract implements Serializable {
    private int id;
    private String url;
    private String contractNo;
    private String contractName;
    private String projectNo;
    private String projectName;
    private String purchaser;
    private String purchaserTelNo;
    private String supplier;
    private String supplierTelNo;
    private String subjectName;
    private String contractValue;
    private String announceDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public String getPurchaserTelNo() {
        return purchaserTelNo;
    }

    public void setPurchaserTelNo(String purchaserTelNo) {
        this.purchaserTelNo = purchaserTelNo;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierTelNo() {
        return supplierTelNo;
    }

    public void setSupplierTelNo(String supplierTelNo) {
        this.supplierTelNo = supplierTelNo;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getContractValue() {
        return contractValue;
    }

    public void setContractValue(String contractValue) {
        this.contractValue = contractValue;
    }

    public String getAnnounceDate() {
        return announceDate;
    }

    public void setAnnounceDate(String announceDate) {
        this.announceDate = announceDate;
    }

    public Contract() {
    }

    public Contract(String url, String contractNo, String contractName, String projectNo, String projectName, String purchaser, String purchaserTelNo, String supplier, String supplierTelNo, String subjectName, String contractValue, String announceDate) {
        this.url = url;
        this.contractNo = contractNo;
        this.contractName = contractName;
        this.projectNo = projectNo;
        this.projectName = projectName;
        this.purchaser = purchaser;
        this.purchaserTelNo = purchaserTelNo;
        this.supplier = supplier;
        this.supplierTelNo = supplierTelNo;
        this.subjectName = subjectName;
        this.contractValue = contractValue;
        this.announceDate = announceDate;
    }
}
