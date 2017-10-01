package com.example.aarong.startechdemo;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.BufferedReader;


public class Api {
    final String DATA_ENDPOINT = "https://platform.shopyourway.com/products/search?" +
            "q=shirt" +
            "&page=%s" +
            "&token=0_20975_253402300799_1_39c0fd9abf524b96985688e78892212c05f34203a46ac36a4117f211b41c7f5d&hash=16eba7802b35f6cb1b03dbf6262d4db0808f437a14f070019a6fa98da45b3d90";

    public ArrayList<Product> get(int page) throws IOException {
        BufferedReader reader = null;
        URL url = new URL(String.format(DATA_ENDPOINT, page));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(5*1000);
        connection.connect();

        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            stringBuilder.append(line + "\n");
        }
        return this.parseApiResult(stringBuilder.toString());
    }

    private ArrayList<Product> parseApiResult(String res) {
        ArrayList<Product> result = new ArrayList<Product>();
        try {
            JSONObject json = new JSONObject(res);
            JSONArray array = json.getJSONArray("products");
            for (int i = 0; i < array.length(); i++) {
                JSONObject productObj = (JSONObject)array.get(i);
                Product product = new Product(
                        productObj.getString("name"),
                        productObj.getString("description"),
                        productObj.getInt("id"),
                        productObj.getString("imageUrl")
                );
                result.add(product);
            }
        } catch (JSONException e) {
            Log.e("Error parsing JSON", e.getMessage());
        }
        return result;
    }

    public class Product {

        int id;
        String name;
        String description;
        String imageUrl;

        Product(String name, String description, int id, String imageUrl) {
            this.name = name;
            this.description = description;
            this.id = id;
            this.imageUrl = imageUrl;
        }


    }
}
