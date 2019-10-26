package com.team.androidpos.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.team.androidpos.model.entity.Sale;
import com.team.androidpos.model.entity.SaleProduct;

import java.util.Date;
import java.util.List;

@Dao
public abstract class SaleDao implements CudDao<Sale> {

    @Insert
    public abstract long insertAndGet(Sale sale);

    @Query("SELECT COUNT(*) FROM Sale")
    public abstract long count();

    @Insert
    public abstract void insert(List<SaleProduct> list);

    @Transaction
    public void save(Sale sale, List<SaleProduct> list) {
        long count = count();
        sale.setVoucherCode("#" + (count > 0 ? count : 1));
        long id = insertAndGet(sale);

        for (SaleProduct sp : list) {
            sp.setSaleId(id);
            sp.getId().setSaleTime(new Date().getTime());
        }

        insert(list);

    }

}
