package com.team.androidpos.ui.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.team.androidpos.ServiceLocator;
import com.team.androidpos.model.repo.ProductRepo;
import com.team.androidpos.model.vo.ProductAndCategoryVO;
import com.team.androidpos.util.AppExecutors;

public class ProductsViewModel extends AndroidViewModel {

    private ProductRepo repo;

    private LiveData<PagedList<ProductAndCategoryVO>> products;

    public ProductsViewModel(@NonNull Application application) {
        super(application);
        this.repo = ServiceLocator.getInstance(application).productRepo();
    }

    LiveData<PagedList<ProductAndCategoryVO>> getProducts() {
        if (products == null) {
            products = repo.getProductAndCategory();
        }
        return products;
    }

    void delete(int id) {
        AppExecutors.io().execute(() -> {
            try {
                repo.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
