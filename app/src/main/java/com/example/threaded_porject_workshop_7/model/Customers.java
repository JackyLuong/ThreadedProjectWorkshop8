package com.example.threaded_porject_workshop_7.model;

import java.io.Serializable;

public class Customers implements Serializable {
    private int custId;
    private String custFirstName;
    private String custLastName;
    private String custAddress;
    private String custCity;
    private String custProvince;
    private String custPostal;
    private String custCountry;
    private String custHomeNum;
    private String custBusNum;
    private String custEmail;
    private int custAgentId;

    //constructor for update or delete
    public Customers(int custId, String custFirstName, String custLastName, String custAddress, String custCity, String custProvince, String custPostal, String custCountry, String custHomeNum, String custBusNum, String custEmail, int custAgentId) {
        this.custId = custId;
        this.custFirstName = custFirstName;
        this.custLastName = custLastName;
        this.custAddress = custAddress;
        this.custCity = custCity;
        this.custProvince = custProvince;
        this.custPostal = custPostal;
        this.custCountry = custCountry;
        this.custHomeNum = custHomeNum;
        this.custBusNum = custBusNum;
        this.custEmail = custEmail;
        this.custAgentId = custAgentId;
    }

    //constructor for add new customer
    public Customers(String custFirstName, String custLastName, String custAddress, String custCity, String custProvince, String custPostal, String custCountry, String custHomeNum, String custBusNum, String custEmail, int custAgentId) {
        this.custFirstName = custFirstName;
        this.custLastName = custLastName;
        this.custAddress = custAddress;
        this.custCity = custCity;
        this.custProvince = custProvince;
        this.custPostal = custPostal;
        this.custCountry = custCountry;
        this.custHomeNum = custHomeNum;
        this.custBusNum = custBusNum;
        this.custEmail = custEmail;
        this.custAgentId = custAgentId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    public String getCustLastName() {
        return custLastName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustProvince() {
        return custProvince;
    }

    public void setCustProvince(String custProvince) {
        this.custProvince = custProvince;
    }

    public String getCustPostal() {
        return custPostal;
    }

    public void setCustPostal(String custPostal) {
        this.custPostal = custPostal;
    }

    public String getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    public String getCustHomeNum() {
        return custHomeNum;
    }

    public void setCustHomeNum(String custHomeNum) {
        this.custHomeNum = custHomeNum;
    }

    public String getCustBusNum() {
        return custBusNum;
    }

    public void setCustBusNum(String custBusNum) {
        this.custBusNum = custBusNum;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public int getCustAgentId() {
        return custAgentId;
    }

    public void setCustAgentId(int custAgentId) {
        this.custAgentId = custAgentId;
    }
}
