package np.com.onlineclothingshop;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DescriptionActivity extends AppCompatActivity {

    private TextView tvName, tvPrice, tvDesc;
    private ImageView imgItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        getSupportActionBar().setTitle("Description");

        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDesc = findViewById(R.id.tvDesc);
        imgItem = findViewById(R.id.imgItem);

        tvName.setText("Item Name: " + getIntent().getStringExtra("name"));
        tvPrice.setText("Price: Rs." + getIntent().getIntExtra("price", 0));
        tvDesc.setText("Description: " + getIntent().getStringExtra("desc"));

        strictMode();
        URL url = null;
        try {
            url = new URL("http://10.0.2.2:3000/" + getIntent().getStringExtra("imageName"));
            imgItem.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void strictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
