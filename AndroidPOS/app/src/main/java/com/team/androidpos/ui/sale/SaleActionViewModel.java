package com.team.androidpos.ui.sale;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.team.androidpos.model.entity.SaleProduct;

import java.util.List;

public class SaleActionViewModel extends AndroidViewModel {

    final MutableLiveData<List<SaleProduct>> saleProducts = new MutableLiveData<>();

    public SaleActionViewModel(@NonNull Application application) {
        super(application);
    }

}
