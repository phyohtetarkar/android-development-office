package com.team.androidpos.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;

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
public class SaleProduct {

    @Embedded
    private SaleProductId id;
    private String name;
    private double price;
    private int quantity;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
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

}
