package com.team.androidpos.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.team.androidpos.model.dao.CategoryDao;
import com.team.androidpos.model.dao.ProductDao;
import com.team.androidpos.model.dao.SaleDao;
import com.team.androidpos.model.entity.Category;
import com.team.androidpos.model.entity.Product;
import com.team.androidpos.model.entity.Sale;
import com.team.androidpos.model.entity.SaleProduct;

@Database(entities = {
        Category.class,
        Product.class,
        Sale.class,
        SaleProduct.class
}, version = 1, exportSchema = false)
@TypeConverters(com.team.androidpos.model.TypeConverters.class)
public abstract class PosDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public abstract SaleDao saleDao();

}
