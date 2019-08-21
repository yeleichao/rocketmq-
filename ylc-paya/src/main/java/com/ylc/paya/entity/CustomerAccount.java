package com.ylc.paya.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerAccount {
    private String account_id;

    private String account_no;

    private Date date_time;

    private BigDecimal current_balance;

    private Integer version;

    private Date create_time;

    private Date update_time;

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id == null ? null : account_id.trim();
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no == null ? null : account_no.trim();
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public BigDecimal getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(BigDecimal current_balance) {
        this.current_balance = current_balance;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}