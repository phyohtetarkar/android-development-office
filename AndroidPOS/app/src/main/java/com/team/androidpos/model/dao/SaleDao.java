package com.team.androidpos.model.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.team.androidpos.model.entity.Sale;
import com.team.androidpos.model.entity.SaleProduct;

import org.joda.time.LocalDateTime;

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

    @Query("SELECT * FROM SALE")
    public abstract DataSource.Factory<Integer, Sale> findAll();

    @Transaction
    public void save(Sale sale, List<SaleProduct> list) {
        long count = count();
        sale.setSaleDateTime(LocalDateTime.now());
        sale.setVoucherCode("#" + (count > 0 ? count + 1 : 1));
        long id = insertAndGet(sale);

        for (SaleProduct sp : list) {
            sp.setSaleId(id);
            sp.getId().setSaleTime(new Date().getTime());
        }

        insert(list);

    }

}
