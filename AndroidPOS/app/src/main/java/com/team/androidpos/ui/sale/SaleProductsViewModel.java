package com.team.androidpos.ui.sale;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import com.team.androidpos.ServiceLocator;
import com.team.androidpos.model.repo.CategoryRepo;
import com.team.androidpos.model.repo.ProductRepo;
import com.team.androidpos.model.vo.ProductAndCategoryVO;

public class SaleProductsViewModel extends AndroidViewModel {
    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;

    final MutableLiveData<Integer> categoryId = new MutableLiveData<>();

    final LiveData<PagedList<ProductAndCategoryVO>> products = Transformations.switchMap(categoryId, id -> {
        return productRepo.getAvailableProduct(id);
    });


    public SaleProductsViewModel(@NonNull Application application) {
        super(application);
        productRepo = ServiceLocator.getInstance(application).productRepo();
        categoryRepo = ServiceLocator.getInstance(application).categoryRepo();
    }

}
