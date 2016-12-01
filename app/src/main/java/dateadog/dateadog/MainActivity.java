package dateadog.dateadog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity implements LikedDogsFragment.OnFragmentInteractionListener,
                                                               SwipeDogsFragment.OnFragmentInteractionListener,
                                                               UserProfileDialogFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getName();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private DADAPI dadapi;
    private SwipeDogsFragment swipeDogsFragment;
    private LikedDogsFragment likedDogsFragment;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dadapi = DADAPI.getInstance(getApplicationContext());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Disable horizontal scrolling of pager to prevent conflicts with
        // swiping of dog cards.
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    if (swipeDogsFragment != null) {
                        swipeDogsFragment.updateUI();
                    }
                } else if (position == 1) {
                    if (likedDogsFragment != null) {
                        likedDogsFragment.updateUI();
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        // Set icons for tabs.
        tabLayout.getTabAt(0).setIcon(R.drawable.dog);
        tabLayout.getTabAt(0).setText(R.string.find_dogs);
        tabLayout.getTabAt(1).setIcon(R.drawable.heart);
        tabLayout.getTabAt(1).setText(R.string.liked_dogs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_log_out) {
            LoginManager.getInstance().logOut();
            Intent logout = new Intent(this, LoginActivity.class);
            logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logout);
            finish();
            return true;
        } else if (id == R.id.action_help) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.help_website))));
        } else if (id == R.id.action_user_profile) {
            dadapi.getUser(new DADAPI.UserProfileDataListener() {
                @Override
                public void onGotUserProfile(UserProfile userProfile) {
                    UserProfileDialogFragment dialog = UserProfileDialogFragment.newInstance(userProfile);
                    dialog.show(getSupportFragmentManager(), "dialog");
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG, uri.toString());
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            if (position == 0) {
                swipeDogsFragment = SwipeDogsFragment.newInstance();
                return swipeDogsFragment;
            } else if (position == 1) {
                likedDogsFragment = LikedDogsFragment.newInstance();
                return likedDogsFragment;
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
