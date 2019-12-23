package com.team.androidpos.ui.report;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.team.androidpos.ServiceLocator;
import com.team.androidpos.model.repo.SaleRepo;
import com.team.androidpos.model.vo.MonthlySaleReportVO;
import com.team.androidpos.model.vo.SaleReportVO;

import java.util.List;

public class SaleReportViewModel extends AndroidViewModel {

    private SaleRepo saleRepo;

    final MutableLiveData<Integer> year = new MutableLiveData<>();

    private LiveData<List<SaleReportVO>> saleReport;

    final LiveData<List<MonthlySaleReportVO>> monthlySaleReport = Transformations.switchMap(year, y -> saleRepo.findMonthlyReport(y));

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
