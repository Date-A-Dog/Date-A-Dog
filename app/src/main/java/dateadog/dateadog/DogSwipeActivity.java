package dateadog.dateadog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import dateadog.dateadog.tindercard.FlingCardListener;
import dateadog.dateadog.tindercard.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.facebook.login.LoginManager;


public class DogSwipeActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface {
    public static MyAppAdapter myAppAdapter; //holds the app adapter
    private TextView noDogs; //displays when there are no dogs left
    public static ViewHolder viewHolder;
    private ArrayList<Data_TinderUI> al;
    private SwipeFlingAdapterView flingContainer;

    public static void removeBackground() {
        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();
    }

    private static void addDogsToAL(Set<Dog> dogs, ArrayList<Data_TinderUI> al) {
        for (Dog dog : dogs) {
            al.add(new Data_TinderUI(dog.getImage(), dog.getName() + "\n" + dog.getAge() + " " + dog.getSex()));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipedog);
        noDogs = (TextView)findViewById(R.id.textNoDogs);
        //noDogs.setText("No More Dogs!");
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        al = new ArrayList<>();
        Set<Dog> dogs = new DADAPI(this).getNextDogs(0);
        addDogsToAL(dogs, al);

        myAppAdapter = new MyAppAdapter(al, DogSwipeActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                if (al.size() == 0) {
                    noDogs.setText("No More Dogs.\nRefresh Page Soon!");
                    noDogs.setTextColor(Color.BLUE);
                }
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                if (al.size() == 0) {
                    noDogs.setText("No More Dogs.\nRefresh Page Soon!");
                    noDogs.setTextColor(Color.BLUE);
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });

    }



    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;

    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Data_TinderUI> parkingList;
        public Context context;

        private MyAppAdapter(List<Data_TinderUI> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.DataText.setTextColor(Color.BLACK);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.background.setBackgroundColor(Color.BLUE);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                viewHolder.cardImage.setBackgroundColor(Color.BLUE);
                viewHolder.cardImage.setMinimumHeight(60);
                viewHolder.cardImage.setMaxHeight(60);

                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            Glide.with(DogSwipeActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
        }
    }
}
