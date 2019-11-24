package com.team.androidpos.ui.sale;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.team.androidpos.ServiceLocator;
import com.team.androidpos.model.entity.Sale;
import com.team.androidpos.model.entity.SaleProduct;
import com.team.androidpos.model.repo.SaleRepo;

import java.util.List;

public class SaleReceiptViewModel extends AndroidViewModel {

    private SaleRepo repo;

    final MutableLiveData<Long> saleId = new MutableLiveData<>();

    public final LiveData<Sale> sale = Transformations.switchMap(saleId, id -> repo.getSale(id));

    public final LiveData<List<SaleProduct>> saleProducts = Transformations.switchMap(saleId, id -> repo.getSaleProducts(id));

    public SaleReceiptViewModel(@NonNull Application application) {
        super(application);
        this.repo = ServiceLocator.getInstance(application).saleRepo();
    }

}
