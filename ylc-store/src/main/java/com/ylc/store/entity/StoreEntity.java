package com.ylc.store.entity;

import java.util.Date;

public class StoreEntity {
    private String store_id;

    private String goods_id;

    private String supplier_id;

    private String goods_name;

    private Integer store_count;

    private Integer version;

    private String create_by;

    private Date create_time;

    private String update_by;

    private Date update_time;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id == null ? null : store_id.trim();
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id == null ? null : goods_id.trim();
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id == null ? null : supplier_id.trim();
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name == null ? null : goods_name.trim();
    }

    public Integer getStore_count() {
        return store_count;
    }

    public void setStore_count(Integer store_count) {
        this.store_count = store_count;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by == null ? null : create_by.trim();
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by == null ? null : update_by.trim();
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}