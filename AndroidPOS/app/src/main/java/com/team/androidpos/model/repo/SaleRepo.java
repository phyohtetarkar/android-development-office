package com.team.androidpos.model.repo;

import com.team.androidpos.model.dao.SaleDao;
import com.team.androidpos.model.entity.Sale;
import com.team.androidpos.model.entity.SaleProduct;

import java.util.List;

public class SaleRepo {

    private SaleDao dao;

    public SaleRepo(SaleDao dao) {
        this.dao = dao;
    }

    public void save(Sale sale, List<SaleProduct> list) {
        dao.save(sale, list);
    }

}
