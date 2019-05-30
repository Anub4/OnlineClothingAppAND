package np.com.onlineclothingshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import np.com.onlineclothingshop.API.Url;
import np.com.onlineclothingshop.models.ItemResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private Button btnAddItem;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().setTitle("Dashboard");

        btnAddItem = findViewById(R.id.btnAddItem);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAddItem.setOnClickListener(v->{
            startActivity(new Intent(this, AddItemActivity.class));
        });

        loadData();
    }

    private void loadData() {
        Url.getEndPoints().getAllItems().enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if(response.body().isStatus()){
                    ItemAdapter adapter = new ItemAdapter(DashboardActivity.this, response.body().getData());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(DashboardActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(recyclerView != null){
            loadData();
        }
    }
}
