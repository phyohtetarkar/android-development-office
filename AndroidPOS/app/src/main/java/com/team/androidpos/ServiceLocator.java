package com.team.androidpos;

import android.content.Context;

import androidx.room.Room;

import com.team.androidpos.model.PosDatabase;
import com.team.androidpos.model.repo.CategoryRepo;
import com.team.androidpos.model.repo.ProductRepo;

public abstract class ServiceLocator {

    private static ServiceLocator instance;

    public abstract CategoryRepo categoryRepo();
    public abstract ProductRepo productRepo();

    public static ServiceLocator getInstance(Context context) {
        if (instance == null) {
            instance = new DefaultServiceLocator(context);
        }
        return instance;
    }

    static class DefaultServiceLocator extends ServiceLocator {

        private PosDatabase database;
        private CategoryRepo categoryRepo;
        private ProductRepo productRepo;

        DefaultServiceLocator(Context context) {
            database = Room.inMemoryDatabaseBuilder(context, PosDatabase.class).build();
        }

        @Override
        public CategoryRepo categoryRepo() {
            if (categoryRepo == null) {
                categoryRepo = new CategoryRepo(database.categoryDao());
            }
            return categoryRepo;
        }

        @Override
        public ProductRepo productRepo() {
            if (productRepo == null) {
                productRepo = new ProductRepo(database.productDao());
            }
            return productRepo;
        }

    }

}
