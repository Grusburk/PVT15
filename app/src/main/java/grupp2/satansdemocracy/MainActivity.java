package grupp2.satansdemocracy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        // Ser till att drawern är synkad om den är utdragen eller inte
        mDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        // till för framtiden kanske
        super.onResume();
    }

    // Skapar en arraylista för drawern där vi lägger till nya "rubriker", använder sedan en switch med onclicklistener för att sätta upp de olika rubrikerna
    private void addDrawerItems() {
        String[] drawerArray = {"News", "Biljetter", "xxx ", "xxx ", "xxxx"};
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drawerArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Log.i(TAG, "position 0");
                        mDrawerLayout.closeDrawers();
                        break;
                    case 1:
                        Log.i(TAG, "position 1");
                        mDrawerLayout.closeDrawers();
                        break;
                    case 2:
                        Log.i(TAG, "position 2");
                        mDrawerLayout.closeDrawers();
                        break;
                    case 3:
                        Log.i(TAG, "position 3");
                        mDrawerLayout.closeDrawers();
                        break;
                    case 4:
                        Log.i(TAG, "position 4");
                        mDrawerLayout.closeDrawers();
                        break;
                }
            }
        });
    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}
