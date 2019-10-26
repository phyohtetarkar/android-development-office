package com.team.androidpos.model.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.team.androidpos.model.entity.Category;
import com.team.androidpos.model.entity.Product;
import com.team.androidpos.model.vo.ProductAndCategoryVO;

@Dao
public interface ProductDao extends CudDao<Product> {

    @Query("SELECT * FROM Product WHERE id = :id LIMIT 1")
    Product findByIdSync(int id);

    @Query("SELECT * FROM Product WHERE id = :id LIMIT 1")
    LiveData<Product> findById(int id);

    @Query("SELECT p.id, p.name, p.image, p.price, c.name AS category FROM Product p " +
            "LEFT JOIN Category c ON p.category_id = c.id " +
            "WHERE p.barcode = :barcode LIMIT 1")
    ProductAndCategoryVO findByBarcode(String barcode);

    @Query("SELECT * FROM Product")
    DataSource.Factory<Integer, Product> findAll();

    @Query("SELECT p.id, p.name, p.image, p.price, c.name AS category FROM Product p " +
            "LEFT JOIN Category c ON p.category_id = c.id ")
    DataSource.Factory<Integer, ProductAndCategoryVO> findProductAndCategory();

    @RawQuery(observedEntities = {Product.class, Category.class})
    DataSource.Factory<Integer, ProductAndCategoryVO> findProductAndCategory(SupportSQLiteQuery query);

}
