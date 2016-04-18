package grupp2.satansdemocracy;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements WikiFragment.OnFragmentInteractionListener,
InformationFragment.OnFragmentInteractionListener, NyheterFragment.OnFragmentInteractionListener,
ForestallningFragment.OnFragmentInteractionListener, BeaconConsumer{

    private Fragment fragment;
    private Toolbar toolbar;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private final String TAG = MainActivity.class.getSimpleName();

    private BeaconManager beaconManager;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerList = (ListView) findViewById(R.id.navList);
        addDrawerItems();
        setupDrawer();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // Ser till att drawern är synkad om den är utdragen eller inte
        mDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        // till för framtiden kanske
        super.onResume();
    }

    /**
     * Skapar en array för drawern där vi lägger till nya "rubriker",
     * använder sedan en switch med onClickListener för att sätta upp de olika rubrikerna
     *
     */
    private void addDrawerItems() {
        String[] drawerArray = {"Start", "Nyheter", "Föreställning ", "Information ", "Wiki+", "Logga ut"};
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drawerArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Log.i(TAG, "position 0");
                        while(getSupportFragmentManager().getBackStackEntryCount()>0){
                            getSupportFragmentManager().popBackStackImmediate();
                        }
                        mDrawerLayout.closeDrawers();
                        break;
                    case 1:
                        Log.i(TAG, "position 1");
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.main_frame, new NyheterFragment())
                                .addToBackStack(null).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case 2:
                        Log.i(TAG, "position 2");
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.main_frame, new ForestallningFragment())
                                .addToBackStack(null).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case 3:
                        Log.i(TAG, "position 3");
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.main_frame, new InformationFragment())
                                .addToBackStack(null).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case 4:
                        Log.i(TAG, "position 4");
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.main_frame, new WikiFragment())
                                .addToBackStack(null).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case 5:
                        Log.i(TAG, "position 5");
                        LoginManager.getInstance().logOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        mDrawerLayout.closeDrawers();
                        break;
                }
            }
        });
    }

    /**
     *
     */
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * Handling the beacon interaction
     * TODO: Should this be in mainActivity? If so, we need to initiate the beacon
     * TODO: only when the föreställningsläge is initiated.
     */
    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setMonitorNotifier(new MonitorNotifier() {

            /**
             * Triggers when a beacon is visible for the first time.
             * @param region
             */
            @Override
            public void didEnterRegion(Region region) {

            }

            /**
             * Triggers when a beacon is no longer visible.
             * @param region
             */
            @Override
            public void didExitRegion(Region region) {

            }

            /**
             *
             * @param i
             * @param region
             */
            @Override
            public void didDetermineStateForRegion(int i, Region region) {

            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
