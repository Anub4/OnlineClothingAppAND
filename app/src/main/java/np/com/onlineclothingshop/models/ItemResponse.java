package np.com.onlineclothingshop.models;

import java.util.ArrayList;

public class ItemResponse {
    private boolean status;
    private String message;
    private ArrayList<Item> data;

    public ItemResponse(boolean status, String message, ArrayList<Item> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Item> getData() {
        return data;
    }

    public void setData(ArrayList<Item> data) {
        this.data = data;
    }
}
