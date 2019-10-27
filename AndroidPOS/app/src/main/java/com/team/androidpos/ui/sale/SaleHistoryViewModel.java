package com.team.androidpos.ui.sale;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import com.team.androidpos.ServiceLocator;
import com.team.androidpos.model.entity.Sale;
import com.team.androidpos.model.repo.SaleRepo;

import org.joda.time.LocalDateTime;

public class SaleHistoryViewModel extends AndroidViewModel {

    private SaleRepo repo;

    final MutableLiveData<LocalDateTime> dateTime = new MutableLiveData<>();

    final LiveData<PagedList<Sale>> sales = Transformations.switchMap(dateTime, dt -> {
        return repo.findAll();
    });

    public SaleHistoryViewModel(@NonNull Application application) {
        super(application);
        repo = ServiceLocator.getInstance(application).saleRepo();
    }

}
