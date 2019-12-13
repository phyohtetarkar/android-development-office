package com.team.androidpos.model.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.team.androidpos.BR;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.Objects;

@Entity
public class Sale extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "voucher_code")
    private String voucherCode;
    @ColumnInfo(name = "total_price")
    private double totalPrice;
    @ColumnInfo(name = "total_product")
    private int totalProduct;
    @ColumnInfo(name = "sale_date_time")
    private DateTime saleDateTime;
    @ColumnInfo(name = "pay_price")
    private double payPrice;
    private int year;
    private int month;
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

    public DateTime getSaleDateTime() {
        return saleDateTime;
    }

    public void setSaleDateTime(DateTime saleDateTime) {
        this.saleDateTime = saleDateTime;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
        this.change = payPrice > 0 ? (payPrice - totalPrice) : 0;
        notifyPropertyChanged(BR.change);
    }

    @Bindable
    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getFormattedDateTime() {
        return saleDateTime.toString("MMM dd, yyyy hh:mm a");
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
