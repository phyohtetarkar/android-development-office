package com.team.androidpos.ui.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.team.androidpos.ServiceLocator;
import com.team.androidpos.model.repo.CategoryRepo;
import com.team.androidpos.model.vo.CategoryAndProductCountVO;
import com.team.androidpos.util.AppExecutors;

import java.util.List;

public class CategoriesViewModel extends AndroidViewModel {

    private CategoryRepo repo;

    private LiveData<List<CategoryAndProductCountVO>> categories;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        this.repo = ServiceLocator.getInstance(application).categoryRepo();
    }

    LiveData<List<CategoryAndProductCountVO>> getCategories() {
        if (categories == null) {
            categories = repo.getCategoryAndProductCount();
        }
        return categories;
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
