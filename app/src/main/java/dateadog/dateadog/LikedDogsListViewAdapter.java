package dateadog.dateadog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class LikedDogsListViewAdapter extends ArrayAdapter<Dog> {

    private Context context;

    public LikedDogsListViewAdapter(Context context, int resourceId, List<Dog> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    private class ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView imageView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Dog dog = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
            holder.descriptionTextView = (TextView) convertView.findViewById(R.id.descriptionTextView);
            holder.imageView = (ImageView) convertView.findViewById(R.id.likedDogImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleTextView.setText(dog.getName());
        holder.descriptionTextView.setText(dog.getBreedsString());
        final ViewHolder holderCopy = holder;
        VolleySingleton.getInstance(context).getImageLoader()
                .get(dog.getImage(), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        holderCopy.imageView.setImageBitmap(response.getBitmap());
                    }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        return convertView;
    }

}