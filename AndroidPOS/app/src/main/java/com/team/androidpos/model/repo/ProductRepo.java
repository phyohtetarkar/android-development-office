package com.team.androidpos.model.repo;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.team.androidpos.model.dao.ProductDao;
import com.team.androidpos.model.entity.Product;
import com.team.androidpos.model.vo.ProductAndCategoryVO;

import java.util.ArrayList;
import java.util.List;

public class ProductRepo extends CudRepo<Product> {

    private ProductDao dao;

    public ProductRepo(ProductDao dao) {
        super(dao);
        this.dao = dao;
    }

    public void save(Product product) {
        if (product.getId() > 0) {
            super.update(product);
        } else {
            super.insert(product);
        }
    }

    public void deleteById(int id) {
        super.delete(dao.findByIdSync(id));
    }

    public Product getProductSync(int id) {
        return dao.findByIdSync(id);
    }

    public LiveData<Product> getProduct(int id) {
        return dao.findById(id);
    }

    public LiveData<PagedList<Product>> getAll() {
        return new LivePagedListBuilder<>(dao.findAll(), 25).build();
    }

    public LiveData<PagedList<ProductAndCategoryVO>> getProductAndCategory() {
        return new LivePagedListBuilder<>(dao.findProductAndCategory(), 25).build();
    }

    public LiveData<PagedList<ProductAndCategoryVO>> getAvailableProduct(@Nullable Integer categoryId) {
        StringBuilder sb = new StringBuilder("SELECT p.id, p.name, p.image, p.price, c.name AS category FROM Product p " +
                "LEFT JOIN Category c ON p.category_id = c.id WHERE p.available = 1 ");

        List<Object> params = new ArrayList<>();

        if (categoryId != null && categoryId > 0) {
            sb.append("AND c.id = ? ");
            params.add(categoryId);
        }

        SimpleSQLiteQuery query = new SimpleSQLiteQuery(sb.toString(), params.toArray());

        return new LivePagedListBuilder<>(dao.findProductAndCategory(query), 25).build();
    }

    public ProductAndCategoryVO findByBarcode(String barcode) {
        return dao.findByBarcode(barcode);
    }

}
