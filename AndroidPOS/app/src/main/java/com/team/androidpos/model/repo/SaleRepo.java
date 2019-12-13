package com.team.androidpos.model.repo;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.team.androidpos.model.dao.SaleDao;
import com.team.androidpos.model.entity.Sale;
import com.team.androidpos.model.entity.SaleProduct;
import com.team.androidpos.model.vo.MonthlySaleReportVO;
import com.team.androidpos.model.vo.SaleReportVO;

import java.util.List;

public class SaleRepo {

    private SaleDao dao;

    public SaleRepo(SaleDao dao) {
        this.dao = dao;
    }

    public long save(Sale sale, List<SaleProduct> list) {
        return dao.save(sale, list);
    }

    public LiveData<PagedList<Sale>> findAll() {
        return new LivePagedListBuilder<>(dao.findAll(), 3).build();
    }

    public LiveData<Sale> getSale(long id) {
        return dao.findById(id);
    }

    public LiveData<List<SaleProduct>> getSaleProducts(long saleId) {
        return dao.findSaleProducts(saleId);
    }

    public LiveData<List<SaleReportVO>> findSaleReport() {
        return dao.findSaleReport();
    }

    public LiveData<List<MonthlySaleReportVO>> findMonthlyReport(int year) {
        return dao.findMonthlyReport(year);
    }

}
