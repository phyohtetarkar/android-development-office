package com.team.androidpos.ui.report;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.team.androidpos.ServiceLocator;
import com.team.androidpos.model.repo.SaleRepo;
import com.team.androidpos.model.vo.SaleReportVO;

import java.util.List;

public class SaleReportViewModel extends AndroidViewModel {

    private SaleRepo saleRepo;

    private LiveData<List<SaleReportVO>> saleReport;

    public SaleReportViewModel(@NonNull Application application) {
        super(application);
        this.saleRepo = ServiceLocator.getInstance(application).saleRepo();
    }

    public LiveData<List<SaleReportVO>> getSaleReport() {
        if (saleReport == null) {
            saleReport = saleRepo.findSaleReport();
        }
        return saleReport;
    }
}
