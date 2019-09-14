package com.team.androidpos.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.joda.time.LocalDateTime;

@Entity
public class Sale {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "voucher_code")
    private String voucherCode;
    @ColumnInfo(name = "total_price")
    private double totalPrice;
    @ColumnInfo(name = "total_product")
    private int totalProduct;
    @ColumnInfo(name = "sale_date_time")
    private LocalDateTime saleDateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public LocalDateTime getSaleDateTime() {
        return saleDateTime;
    }

    public void setSaleDateTime(LocalDateTime saleDateTime) {
        this.saleDateTime = saleDateTime;
    }
}
