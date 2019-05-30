package np.com.onlineclothingshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import np.com.onlineclothingshop.models.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder> {

    private Context context;
    private ArrayList<Item> listItems;

    public ItemAdapter(Context context, ArrayList<Item> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item, viewGroup, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        Item item = listItems.get(i);
        holder.tvName.setText(item.getName());
        strictMode();
        URL url = null;
        try {
            url = new URL("http://10.0.2.2:3000/" + item.getImageName());
            holder.imgItem.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.itemView.getRootView().setOnClickListener(v->{
            Intent intent = new Intent(context, DescriptionActivity.class);
            intent.putExtra("name", item.getName());
            intent.putExtra("price", item.getPrice());
            intent.putExtra("imageName", item.getImageName());
            intent.putExtra("desc", item.getDesc());
            context.startActivity(intent);
        });
    }

    private void strictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView imgItem;
        private TextView tvName;

        public Holder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgItem);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
