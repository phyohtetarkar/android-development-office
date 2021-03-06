package com.team.androidpos.ui.sale;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapePath;
import com.google.android.material.shape.ShapePathModel;
import com.team.androidpos.R;
import com.team.androidpos.databinding.SaleReceiptBinding;
import com.team.androidpos.model.entity.SaleProduct;
import com.team.androidpos.ui.MainActivity;

public class SaleReceiptFragment extends Fragment {

    static final String KEY_SALE_ID = "sale_id";
    static final String KEY_NAV_BACK = "nav_back";

    static final int NAV_SALE_PRODUCT = 1;
    static final int NAV_SALE_HISTORY = 2;

    private SaleReceiptBinding binding;
    private SaleReceiptViewModel viewModel;

    static class ConcaveCornerTreatment extends CornerTreatment {
        private float size;

        public ConcaveCornerTreatment(float size) {
            this.size = size;
        }

        @Override
        public void getCornerPath(float angle, float interpolation, ShapePath shapePath) {
            shapePath.reset(5f, 55);
            shapePath.quadToPoint(55, 55 * interpolation, 55, 5);

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = ViewModelProviders.of(this).get(SaleReceiptViewModel.class);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        int nav = getArguments() != null ? getArguments().getInt(KEY_NAV_BACK, 1) : 1;
        if (nav == NAV_SALE_PRODUCT) {
            inflater.inflate(R.menu.menu_finish, menu);
            MainActivity activity = (MainActivity) requireActivity();
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_finish) {
            Navigation.findNavController(getView()).popBackStack(R.id.saleProductsFragment, false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SaleReceiptBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ShapePathModel shapePathModel = new ShapePathModel();
        shapePathModel.setAllCorners(new ConcaveCornerTreatment(0));
        MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable(shapePathModel);
        shapeDrawable.setTint(ContextCompat.getColor(requireContext(), R.color.colorPrimary));
        shapeDrawable.setPaintStyle(Paint.Style.FILL);
        shapeDrawable.setShadowEnabled(true);
        shapeDrawable.setShadowColor(Color.BLACK);
        binding.shapedCardView.setBackground(shapeDrawable);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) requireActivity();
        activity.switchToggle(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        long saleId = getArguments() != null ? getArguments().getLong(KEY_SALE_ID, 0) : 0;
        if (saleId > 0) viewModel.saleId.setValue(saleId);

        viewModel.saleProducts.observe(this, list -> {

            binding.linearLayoutItems.removeAllViews();

            for (SaleProduct sp : list) {
                View itemView = getLayoutInflater().inflate(R.layout.layout_receipt_item, binding.linearLayoutItems, false);
                TextView tvQty = itemView.findViewById(R.id.tvQty);
                TextView tvItemDesc = itemView.findViewById(R.id.tvItemDesc);
                TextView tvPrice = itemView.findViewById(R.id.tvPrice);

                tvQty.setText(String.valueOf(sp.getQuantity()));
                tvItemDesc.setText(sp.getProductDescription());

                double total = sp.getTotalPrice();
                int abs = (int) total;

                if ((total - abs) > 0.0) {
                    tvPrice.setText(String.valueOf(total));
                } else {
                    tvPrice.setText(String.valueOf(abs));
                }

                binding.linearLayoutItems.addView(itemView);
            }

            binding.linearLayoutItems.invalidate();

        });
    }
}
