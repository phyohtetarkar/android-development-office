package com.team.androidpos.model.repo;

import androidx.lifecycle.LiveData;

import com.team.androidpos.model.dao.CategoryDao;
import com.team.androidpos.model.entity.Category;
import com.team.androidpos.model.vo.CategoryAndProductCountVO;

import java.util.List;

public class CategoryRepo extends CudRepo<Category> {

    private CategoryDao dao;

    public CategoryRepo(CategoryDao dao) {
        super(dao);
        this.dao = dao;
    }

    public void save(Category category) {
        if (category.getId() > 0) {
            super.update(category);
        } else {
            super.insert(category);
        }
    }

    public void deleteById(int id) {
        super.delete(dao.findByIdSync(id));
    }

    public Category getCategorySync(int id) {
        return dao.findByIdSync(id);
    }

    public LiveData<Category> getCategory(int id) {
        return dao.findById(id);
    }

    public LiveData<List<Category>> getAll() {
        return dao.findAll();
    }

    public List<Category> getAllSync() {
        return dao.findAllSync();
    }

    public LiveData<List<CategoryAndProductCountVO>> getCategoryAndProductCount() {
        return dao.findCategoryAndProductCount();
    }

}
