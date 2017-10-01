package com.example.aarong.startechdemo;
import com.example.aarong.startechdemo.Product;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


import android.view.ViewGroup;

public class CustomListAdapter extends BaseAdapter {

    private ArrayList<Product> data;
    private ScrollingActivity scrollingActivity;

    public CustomListAdapter(ScrollingActivity context, ArrayList<Product> data) {
        this.data = data;
        this.scrollingActivity = context;
    }

    class ViewHolder {
        private TextView name;
        private TextView description;
        private ImageView image;
        private AsyncTask imageLoadTask;

        private void setImage(String imageUrl) {
            //TODO
        }

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).id;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final Product product = data.get(i);
        ViewHolder holder;
        View resultView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.scrollingActivity.getSystemService
                    (this.scrollingActivity.LAYOUT_INFLATER_SERVICE);
            resultView = inflater.inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.name = ((TextView)resultView.findViewById(R.id.name));
            holder.description = ((TextView)resultView.findViewById(R.id.description));
            holder.image = ((ImageView)resultView.findViewById(R.id.image));
            resultView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            resultView = convertView;
        }

        setHolderValue(holder, product);

        return resultView;
    }

    private void setHolderValue(ViewHolder holder, Product product) {
        holder.name.setText(product.name);
        holder.description.setText(product.description);
        holder.setImage(product.imageUrl);
    }

}

