package com.team.androidpos.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.team.androidpos.model.dao.CategoryDao;
import com.team.androidpos.model.dao.ProductDao;
import com.team.androidpos.model.entity.Category;
import com.team.androidpos.model.entity.Product;

@Database(entities = {
        Category.class,
        Product.class
}, version = 1, exportSchema = false)
public abstract class PosDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

}
