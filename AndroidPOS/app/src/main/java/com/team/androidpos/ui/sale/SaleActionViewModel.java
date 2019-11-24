package com.team.androidpos.ui.sale;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.team.androidpos.ServiceLocator;
import com.team.androidpos.model.entity.Sale;
import com.team.androidpos.model.entity.SaleProduct;
import com.team.androidpos.model.repo.ProductRepo;
import com.team.androidpos.model.repo.SaleRepo;
import com.team.androidpos.model.vo.ProductAndCategoryVO;
import com.team.androidpos.util.AppExecutors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaleActionViewModel extends AndroidViewModel {

    private SaleRepo saleRepo;
    private ProductRepo productRepo;
    private boolean inProgress;

    final MutableLiveData<Map<SaleProduct.SaleProductId, SaleProduct>> saleProducts = new MutableLiveData<>();
    final MutableLiveData<Long> saleResult = new MutableLiveData<>();

    public final MutableLiveData<Sale> sale = new MutableLiveData<>();
    public final MutableLiveData<SaleProduct> editSaleProduct = new MutableLiveData<>();

    public SaleActionViewModel(@NonNull Application application) {
        super(application);
        this.saleRepo = ServiceLocator.getInstance(application).saleRepo();
        this.productRepo = ServiceLocator.getInstance(application).productRepo();
    }

    void init() {
        if (!inProgress) {
            sale.setValue(new Sale());
            saleProducts.setValue(new HashMap<>());
            inProgress = true;
        }
    }

    void findByBarcode(String barcode) {
        AppExecutors.io().execute(() -> {
            ProductAndCategoryVO vo = productRepo.findByBarcode(barcode);
            if (vo != null) {
                AppExecutors.main().execute(() -> {
                    addProduct(vo);
                });
            }
        });
    }

    void addProduct(ProductAndCategoryVO vo) {
        Map<SaleProduct.SaleProductId, SaleProduct> map = saleProducts.getValue() != null ?
                saleProducts.getValue() : new HashMap<>();

        SaleProduct sp = vo.toSaleProduct();
        SaleProduct old = map.get(sp.getId());

        if (old != null) {
            old.setQuantity(old.getQuantity() + 1);
        } else {
            sp.setQuantity(1);
            map.put(sp.getId(), sp);
        }

        saleProducts.setValue(map);
        computeSale();
    }

    void removeProduct(SaleProduct saleProduct) {
        Map<SaleProduct.SaleProductId, SaleProduct> map = saleProducts.getValue();
        if (map != null) {
            map.remove(saleProduct.getId());
            saleProducts.setValue(map);
            computeSale();
        }
    }

    void finishSale() {
        AppExecutors.io().execute(() -> {
            try {
                long saleId = saleRepo.save(sale.getValue(), new ArrayList<>(saleProducts.getValue().values()));
                inProgress = false;
                saleProducts.postValue(null);
                sale.postValue(null);
                saleResult.postValue(saleId);
            } catch (Exception e) {
                e.printStackTrace();
                saleResult.postValue(null);
            }
        });
    }

    void computeSale() {
        Map<SaleProduct.SaleProductId, SaleProduct> map = saleProducts.getValue() != null ?
                saleProducts.getValue() : new HashMap<>();
        Sale s = sale.getValue();
        if (s != null) {
            int sum = 0;
            int count = 0;
            for (SaleProduct sp : map.values()) {
                count += sp.getQuantity();
                sum += sp.getQuantity() * sp.getPrice();
            }

            s.setTotalProduct(count);
            s.setTotalPrice(sum);

            sale.setValue(s);
        }
    }

    void saveEditSaleProduct() {
        SaleProduct sp = editSaleProduct.getValue();
        if (sp != null) {
            Map<SaleProduct.SaleProductId, SaleProduct> map = saleProducts.getValue() != null ?
                    saleProducts.getValue() : new HashMap<>();
            map.put(sp.getId(), sp);
            saleProducts.setValue(map);
            computeSale();
        }
    }

    public void plusQty() {
        SaleProduct sp = editSaleProduct.getValue();
        if (sp != null) {
            sp.setQuantity(sp.getQuantity() + 1);
        }
    }

    public void minusQty() {
        SaleProduct sp = editSaleProduct.getValue();
        if (sp != null && sp.getQuantity() > 1) {
            sp.setQuantity(sp.getQuantity() - 1);
        }
    }

    public boolean isInProgress() {
        return inProgress;
    }
}
