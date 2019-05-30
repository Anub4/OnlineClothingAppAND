package np.com.onlineclothingshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import np.com.onlineclothingshop.API.Url;
import np.com.onlineclothingshop.models.Response;
import np.com.onlineclothingshop.models.User;
import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etUsername, etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            if (!validate()) return;
            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            User user = new User(0, firstName, lastName, username, password);
            Url.getEndPoints().register(user).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if(response.body().isStatus()){
                        Toast.makeText(RegisterActivity.this, "User registered successfully!!!", Toast.LENGTH_SHORT).show();
                        RegisterActivity.super.onBackPressed();
                    }else{
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            etFirstName.setError("Please enter first name.");
            etFirstName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etLastName.getText().toString())) {
            etLastName.setError("Please enter last name.");
            etLastName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError("Please enter username.");
            etUsername.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Please enter password.");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }
}
