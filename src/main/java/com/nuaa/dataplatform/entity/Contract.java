package com.nuaa.dataplatform.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

public class Contract implements Serializable {
    private int id;
    private String contractNo;
    private String contractName;
    private String projectNo;
    private String projectName;
    private String purchaser;
    private String purchaserTelNo;
    private String supplier;
    private String supplierTelNo;
    private String subjectName;
    private float subjectUnitPrice;
    private float contractValue;
    private Date announceDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getSubjectUnitPrice() {
        return subjectUnitPrice;
    }

    public void setSubjectUnitPrice(float subjectUnitPrice) {
        this.subjectUnitPrice = subjectUnitPrice;
    }

    public float getContractValue() {
        return contractValue;
    }

    public void setContractValue(float contractValue) {
        this.contractValue = contractValue;
    }

    public Date getAnnounceDate() {
        return announceDate;
    }

    public void setAnnounceDate(Date announceDate) {
        this.announceDate = announceDate;
    }

    public Contract(int id, String contractNo, String contractName, String projectNo, String projectName,
                    String purchaser, String purchaserTelNo, String supplier, String supplierTelNo,
                    String subjectName, float subjectUnitPrice, float contractValue, Date announceDate) {
        this.id = id;
        this.contractNo = contractNo;
        this.contractName = contractName;
        this.projectNo = projectNo;
        this.projectName = projectName;
        this.purchaser = purchaser;
        this.purchaserTelNo = purchaserTelNo;
        this.supplier = supplier;
        this.supplierTelNo = supplierTelNo;
        this.subjectName = subjectName;
        this.subjectUnitPrice = subjectUnitPrice;
        this.contractValue = contractValue;
        this.announceDate = announceDate;
    }

    public Contract(String contractNo, String contractName, String projectNo, String projectName,
                    String purchaser, String purchaserTelNo, String supplier, String supplierTelNo,
                    String subjectName, float subjectUnitPrice, float contractValue, Date announceDate) {
        this.contractNo = contractNo;
        this.contractName = contractName;
        this.projectNo = projectNo;
        this.projectName = projectName;
        this.purchaser = purchaser;
        this.purchaserTelNo = purchaserTelNo;
        this.supplier = supplier;
        this.supplierTelNo = supplierTelNo;
        this.subjectName = subjectName;
        this.subjectUnitPrice = subjectUnitPrice;
        this.contractValue = contractValue;
        this.announceDate = announceDate;
    }
}
