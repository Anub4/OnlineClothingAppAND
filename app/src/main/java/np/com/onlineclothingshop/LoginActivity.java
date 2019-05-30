package np.com.onlineclothingshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import np.com.onlineclothingshop.API.Url;
import np.com.onlineclothingshop.models.Response;
import np.com.onlineclothingshop.models.User;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            if (!validate()) return;
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            Url.getEndPoints().login(new User(username, password)).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.body().isStatus()) {
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(etUsername.getText().toString())) {
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

    public void openRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}