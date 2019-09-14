package com.team.androidpos.ui.product;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.team.androidpos.R;
import com.team.androidpos.databinding.ProductEditBinding;
import com.team.androidpos.model.entity.Category;
import com.team.androidpos.model.entity.Product;
import com.team.androidpos.ui.MainActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class ProductEditFragment extends Fragment {

    static final String KEY_PRODUCT_ID = "product_id";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSION_TAKE_PHOTO = 2;

    private ProductEditViewModel viewModel;
    private ProductEditBinding binding;
    private String currentPhotoFilePath;

    private final ChipGroup.OnCheckedChangeListener chipCheckListener = (chipGroup, id) -> {
        if (getView() == null) return;

        Chip chip = getView().findViewById(id);

        Product product = viewModel.product.getValue();

        if (chip == null) {
            product.setCategoryId(0);
            return;
        }

        product.setCategoryId((Integer) chip.getTag());
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MainActivity activity = (MainActivity) requireActivity();
        activity.switchToggle(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this).get(ProductEditViewModel.class);

        viewModel.categories.observe(this, categories -> {
            View view = getView();
            if (view == null) return;

            ChipGroup categoryGroup = view.findViewById(R.id.chipGroupCategories);
            categoryGroup.removeAllViews();

            Product product = viewModel.product.getValue();

            for (Category c : categories) {
                Chip chip = new Chip(view.getContext());
                chip.setCheckable(true);
                chip.setText(c.getName());
                chip.setTag(c.getId());

                if (product.getCategoryId() == c.getId()) {
                    chip.setChecked(true);
                }

                categoryGroup.addView(chip);
            }

            categoryGroup.invalidate();
            categoryGroup.setOnCheckedChangeListener(chipCheckListener);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProductEditBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            viewModel.save();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnScan = view.findViewById(R.id.btnScan);
        btnScan.setOnClickListener(v -> {
            IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
            integrator.setOrientationLocked(false);
            integrator.initiateScan();
        });

        ImageButton btnTakePhoto = view.findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(requireContext());

            View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_take_picture_action, null);
            TextView tvTakePhoto = bottomSheetView.findViewById(R.id.tvTakePhoto);

            tvTakePhoto.setOnClickListener(tv -> {
                dialog.dismiss();
                dispatchTakePictureIntent();
            });

            dialog.setContentView(bottomSheetView);
            dialog.show();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.operation.observe(this, op -> {
            if (op) requireActivity().onBackPressed();
        });

        int id = getArguments() != null ? getArguments().getInt(KEY_PRODUCT_ID) : 0;
        viewModel.init(id);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && currentPhotoFilePath != null) {
            binding.btnTakePhoto.setImageURI(Uri.parse(currentPhotoFilePath));
            viewModel.product.getValue().setImage(currentPhotoFilePath);
        } else if (result != null) {
            if (result.getContents() != null) {
                Product product = viewModel.product.getValue();
                product.setBarcode(result.getContents());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_TAKE_PHOTO) {
            if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity activity = (MainActivity) requireActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.switchToggle(true);
        activity.hideKeyboard();
    }

    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    PERMISSION_TAKE_PHOTO);
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                // TODO show error message
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "com.team.androidpos.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(imageFileName, ".jpg",
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        currentPhotoFilePath = image.getAbsolutePath();
        return image;
    }
}
