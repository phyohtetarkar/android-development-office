package com.team.androidpos.ui.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.team.androidpos.ServiceLocator;
import com.team.androidpos.model.entity.Category;
import com.team.androidpos.model.entity.Product;
import com.team.androidpos.model.repo.CategoryRepo;
import com.team.androidpos.model.repo.ProductRepo;
import com.team.androidpos.util.AppExecutors;

import java.util.List;

public class ProductEditViewModel extends AndroidViewModel {

    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;

    public final MutableLiveData<Product> product = new MutableLiveData<>();

    final MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    final MutableLiveData<Boolean> operation = new MutableLiveData<>();

    public ProductEditViewModel(@NonNull Application application) {
        super(application);
        this.productRepo = ServiceLocator.getInstance(application).productRepo();
        this.categoryRepo = ServiceLocator.getInstance(application).categoryRepo();
    }

    void init(int id) {
        AppExecutors.io().execute(() -> {
            Product p = productRepo.getProductSync(id);
            if (p != null) {
                product.postValue(p);
            } else {
                product.postValue(new Product());
            }

            categories.postValue(categoryRepo.getAllSync());
        });
    }

    public void save() {
        if (isValid()) {
            AppExecutors.io().execute(() -> {
                try {
                    productRepo.save(product.getValue());
                    operation.postValue(true);
                } catch (Exception e) {
                    operation.postValue(false);
                }
            });
        }
    }

    public boolean isValid() {
        // TODO
        return true;
    }

}
