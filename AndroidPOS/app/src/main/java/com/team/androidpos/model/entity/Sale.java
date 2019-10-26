package com.team.androidpos.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.joda.time.LocalDateTime;

import java.util.Objects;

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
    @ColumnInfo(name = "pay_price")
    private double payPrice;
    private double change;

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

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return id == sale.id &&
                Double.compare(sale.totalPrice, totalPrice) == 0 &&
                totalProduct == sale.totalProduct &&
                Double.compare(sale.payPrice, payPrice) == 0 &&
                Double.compare(sale.change, change) == 0 &&
                Objects.equals(voucherCode, sale.voucherCode) &&
                Objects.equals(saleDateTime, sale.saleDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, voucherCode, totalPrice, totalProduct, saleDateTime, payPrice, change);
    }
}
