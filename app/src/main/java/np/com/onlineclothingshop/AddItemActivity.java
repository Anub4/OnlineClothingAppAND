package np.com.onlineclothingshop;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import np.com.onlineclothingshop.API.Url;
import np.com.onlineclothingshop.models.Item;
import np.com.onlineclothingshop.models.Response;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AddItemActivity extends AppCompatActivity {

    private EditText etName, etPrice, etDesc;
    private ImageView imgItem;
    private Button btnAddItem;
    private String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        getSupportActionBar().setTitle("Add Item");
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etDesc = findViewById(R.id.etDesc);
        imgItem = findViewById(R.id.imgItem);
        btnAddItem = findViewById(R.id.btnAddItem);

        imgItem.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 0);
        });
        btnAddItem.setOnClickListener(v -> {
            if (!validate()) {
                return;
            }
            File file = new File(imagePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("itemImage", file.getName(), mFile);
            Url.getEndPoints().uploadImage(fileToUpload).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if(response.body().isStatus()){
                        addItem(response.body().getMessage());
                    }else {
                        Toast.makeText(AddItemActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Toast.makeText(AddItemActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void addItem(String imageName){
        String name = etName.getText().toString();
        int price = Integer.parseInt(etPrice.getText().toString());
        String desc = etDesc.getText().toString();
        Item item = new Item(0, name, price, imageName, desc);
        Url.getEndPoints().addItem(item).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.body().isStatus()){
                    Toast.makeText(AddItemActivity.this, "Item Added Successfully!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(AddItemActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            etName.setError("Please enter item name.");
            etName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etPrice.getText().toString())) {
            etPrice.setError("Please enter price.");
            etPrice.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etDesc.getText().toString())) {
            etDesc.setError("Please enter description.");
            etDesc.requestFocus();
            return false;
        } else if (imagePath.equals("")) {
            Toast.makeText(this, "Please select image.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            imagePath = getPath(uri);
            imgItem.setImageURI(uri);
        }
    }

    public String getPath(Uri uri) {
        String[] projectile = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projectile, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }
}
