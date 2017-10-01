package com.example.aarong.startechdemo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


import android.view.ViewGroup;

public class CustomListAdapter extends BaseAdapter {

    private ArrayList<Api.Product> data;
    private ScrollingActivity scrollingActivity;
    private LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(14440000 * 15) {
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };

    public CustomListAdapter(ScrollingActivity context, ArrayList<Api.Product> data) {
        this.data = data;
        this.scrollingActivity = context;
    }

    class ViewHolder {
        private TextView name;
        private TextView description;
        private ImageView image;
        private AsyncTask imageLoadTask;

        private void setImage(String imageUrl) {
            this.loadAndSetImage(imageUrl, this.image, this.imageLoadTask);
        }

        private void loadAndSetImage(final String imageUrl, final ImageView imageView, AsyncTask imageLoadTask) {
            Bitmap bitmap = imageCache.get(imageUrl);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                if (imageLoadTask != null) {
                    imageLoadTask.cancel(true);
                }
                AsyncTask task = new AsyncTask() {
                    @Override
                    protected void onPreExecute() {
                        imageView.setImageResource(R.color.colorAccent);
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object[] params) {
                        String imageUrl = (String) params[0];
                        Bitmap result = getBitmapFromUrl(imageUrl);
                        imageCache.put(imageUrl, result);
                        return result;
                    }

                    @Override
                    protected void onPostExecute(Object result) {
                        imageView.setImageBitmap((Bitmap) result);
                    }
                };
                this.imageLoadTask = task;
                task.execute(imageUrl);
            }
        }

        private Bitmap getBitmapFromUrl(String imageUrl) {
            InputStream input = null;
            try {
                input = new URL(imageUrl).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Image Download", imageUrl);
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            Log.d("Image Size", Integer.toString(bitmap.getByteCount()));
            return bitmap;
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
        final Api.Product product = data.get(i);
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

    private void setHolderValue(ViewHolder holder, Api.Product product) {
        holder.name.setText(product.name);
        holder.description.setText(product.description);
        holder.setImage(product.imageUrl);
    }

}

