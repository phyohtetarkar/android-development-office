package com.team.androidpos.model.repo;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.team.androidpos.model.dao.ProductDao;
import com.team.androidpos.model.entity.Product;
import com.team.androidpos.model.vo.ProductAndCategoryVO;

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

}
