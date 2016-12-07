package dateadog.dateadog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LikedDogsRecyclerViewAdapter extends RecyclerView.Adapter {

    private static class ViewHolder extends RecyclerView.ViewHolder {

        public Dog dog;
        public FrameLayout layout;
        public TextView name;
        public ImageView cardImage;
        public ImageView approved;
        public ImageView rejected;
        public ImageView pending;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private Activity activity;
    private List<Dog> likedDogs;

    public LikedDogsRecyclerViewAdapter(Activity activity, List<Dog> likedDogs) {
        this.activity = activity;
        this.likedDogs = likedDogs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.liked_dog_tile, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.layout = (FrameLayout) view.findViewById(R.id.likedDogFrame);
        viewHolder.layout.setTag(viewHolder);
        viewHolder.name = (TextView) view.findViewById(R.id.likedDogName);
        viewHolder.cardImage = (ImageView) view.findViewById(R.id.likedDogImage);
        viewHolder.approved = (ImageView) view.findViewById(R.id.approved);
        viewHolder.rejected = (ImageView) view.findViewById(R.id.rejected);
        viewHolder.pending = (ImageView) view.findViewById(R.id.pending);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= likedDogs.size()) {
            return;
        }
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.dog = likedDogs.get(position);
        viewHolder.name.setText(likedDogs.get(position).getName());

        final FutureTarget<GlideDrawable> request = Glide.with(activity)
                .load(likedDogs.get(position).getImageURL()).centerCrop()
                .into(viewHolder.cardImage.getWidth(), viewHolder.cardImage.getHeight());
        new AsyncTask<Void, Void, GlideDrawable>() {
            @Override
            protected void onPreExecute() {
                viewHolder.cardImage.setImageResource(R.drawable.loading_spinner);
            }

            @Override
            protected GlideDrawable doInBackground(Void... voids) {
                try {
                    return request.get();
                } catch (InterruptedException e) {

                } catch (ExecutionException e) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(GlideDrawable glideDrawable) {
                viewHolder.cardImage.setImageDrawable(glideDrawable);
            }
        }.execute();

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showDogProfile = new Intent(activity, DogProfileActivity.class);
                showDogProfile.putExtra("Dog", ((ViewHolder) view.getTag()).dog);
                activity.startActivity(showDogProfile);
            }
        });
        viewHolder.approved.setVisibility(View.INVISIBLE);
        viewHolder.rejected.setVisibility(View.INVISIBLE);
        viewHolder.pending.setVisibility(View.INVISIBLE);
        if (likedDogs.get(position).getDateRequest() != null) {
            DateRequest.Status status = likedDogs.get(position).getDateRequest().getStatus();
            if (status == DateRequest.Status.APPROVED) {
                viewHolder.approved.setVisibility(View.VISIBLE);
            } else if (status == DateRequest.Status.REJECTED) {
                viewHolder.rejected.setVisibility(View.VISIBLE);
            } else if (status == DateRequest.Status.PENDING) {
                viewHolder.pending.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return likedDogs.size();
    }
}
