package com.team.androidpos.model.vo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import com.team.androidpos.model.entity.Category;

import java.util.Objects;

public class CategoryAndProductCountVO {

    @Embedded
    private Category category;
    @ColumnInfo(name = "product_count")
    private long productCount;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryAndProductCountVO that = (CategoryAndProductCountVO) o;
        return productCount == that.productCount &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, productCount);
    }

    @NonNull
    @Override
    public String toString() {
        return category.getName();
    }
}
