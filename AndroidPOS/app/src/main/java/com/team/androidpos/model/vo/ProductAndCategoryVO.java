package com.team.androidpos.model.vo;

import java.util.Objects;

public class ProductAndCategoryVO {

    private int id;
    private String name;
    private String image;
    private String category;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAndCategoryVO that = (ProductAndCategoryVO) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(image, that.image) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, category, price);
    }
}
