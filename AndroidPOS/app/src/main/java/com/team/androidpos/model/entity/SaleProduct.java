package com.team.androidpos.model.entity;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.team.androidpos.BR;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;

@Entity(tableName = "sale_product",
        primaryKeys = {"product_id", "sale_time"},
        foreignKeys = {
        @ForeignKey(
                entity = Product.class,
                parentColumns = "id",
                childColumns = "product_id"
        ),
        @ForeignKey(
                entity = Sale.class,
                parentColumns = "id",
                childColumns = "sale_id"
        )
})
public class SaleProduct extends BaseObservable {

    @NonNull
    @Embedded
    private SaleProductId id;
    private String name;
    private double price;
    private int quantity;
    private String category;

    @ColumnInfo(name = "sale_id")
    private long saleId;

    public SaleProduct() {
        id = new SaleProductId();
    }

    public SaleProductId getId() {
        return id;
    }

    public void setId(SaleProductId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Bindable
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        notifyPropertyChanged(BR.totalPrice);
        notifyPropertyChanged(BR.quantity);
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriceAndCount() {
        DecimalFormat df = new DecimalFormat();
        df.setRoundingMode(RoundingMode.CEILING);
        return String.format(Locale.ENGLISH, "%d x %s", quantity, df.format(price));
    }

    @Bindable
    public double getTotalPrice() {
        return quantity * price;
    }

    public SaleProduct copy() {
        SaleProduct sp = new SaleProduct();
        sp.setId(id);
        sp.setQuantity(quantity);
        sp.setCategory(category);
        sp.setSaleId(saleId);
        sp.setPrice(price);
        sp.setName(name);
        return sp;
    }

    @Override
    public String toString() {
        return "SaleProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", saleId=" + saleId +
                '}';
    }

    public class SaleProductId {
        @ColumnInfo(name = "product_id")
        private int productId;
        @ColumnInfo(name = "sale_time")
        private long saleTime;

        public SaleProductId() {}

        public SaleProductId(int productId, long saleTime) {
            this.productId = productId;
            this.saleTime = saleTime;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public long getSaleTime() {
            return saleTime;
        }

        public void setSaleTime(long saleTime) {
            this.saleTime = saleTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SaleProductId that = (SaleProductId) o;
            return productId == that.productId &&
                    saleTime == that.saleTime;
        }

        @Override
        public int hashCode() {
            return Objects.hash(productId, saleTime);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleProduct that = (SaleProduct) o;
        return quantity == that.quantity &&
                saleId == that.saleId &&
                id.equals(that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, saleId);
    }
}
