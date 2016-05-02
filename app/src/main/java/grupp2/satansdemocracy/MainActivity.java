package grupp2.satansdemocracy;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import org.w3c.dom.Text;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView infoText;
    private Intent notificationIntent;
    private PendingIntent pendingIntent;
    private Notification.Builder notificationBuilder;
    private AlertDialog.Builder forestallningsDialog;
    private Button beaconButton;
    private ImageSwitcher lampSwitcher;
    private boolean beaconMode;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String notificationID = "1";
    private String notificationTitle, notificationText;
    private final String TAG = MainActivity.class.getSimpleName();
    private List<String> found = new ArrayList<>();
    private List<String> used = new ArrayList<>();
    private BluetoothAdapter bluetoothAdapter;
    private boolean isBtEnable = false;
    private final String jZiggy = "CC:69:C6:5B:13:D7";
    private final String jDonny = "FF:FF:50:01:25:63";
    private final String aZiggy = "F2:E1:A3:7E:CF:BC";
    private final String aDonny = "FF:FF:70:01:4C:E6";
    private ScanFilter jZiggyFilter = new ScanFilter.Builder().setDeviceAddress(jZiggy).build();
    private ScanFilter jDonnyFilter = new ScanFilter.Builder().setDeviceAddress(jDonny).build();
    private ScanFilter aZiggyFilter = new ScanFilter.Builder().setDeviceAddress(aZiggy).build();
    private ScanFilter aDonnyFilter = new ScanFilter.Builder().setDeviceAddress(aDonny).build();
    private List<ScanFilter> filterList;
    private ScanCallback scanCallback;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private ScanSettings scanSettings = new ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .build();
    private DBHandler dbHandler = new DBHandler();
    private MessageHandler messageHandler;
    private String profileID;

    /**
     * Sets up an instance oc the mainActivity class upon first creation.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        infoText = (TextView) findViewById(R.id.show_info);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerList = (ListView) findViewById(R.id.navList);
        beaconButton = (Button) findViewById(R.id.beacons_button);
        lampSwitcher = (ImageSwitcher) findViewById(R.id.lamp_switcher);
        addDrawerItems();
        setUpDrawer();
        uiStuff();
        mDrawerToggle.syncState();
        notificationBuilder = (Notification.Builder) new Notification.Builder(getApplicationContext());
        forestallningsDialog = new AlertDialog.Builder(MainActivity.this,android.R.style.Theme_Holo_Dialog_NoActionBar);

        /**
         * Beacon related initiation
         */

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        filterList = new ArrayList<>();
        filterList.add(jZiggyFilter);
        filterList.add(jDonnyFilter);
        filterList.add(aZiggyFilter);
        filterList.add(aDonnyFilter);

        /**
         * Get profile ID from LoginActivity
         */
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            profileID = extras.getString("id");
        }
        messageHandler = new MessageHandler(profileID);

        /**
         * Request access for Beacon Searching
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
    }

    /**
     * Called for marshmallow access for permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * Creates an array for the navigation drawer in which to add headers.
     * Then using a switch statement to set up the headers.
     */
    private void addDrawerItems() {
        String[] drawerArray = {"FÖRESTÄLLNING", "#SATANSDEMOKRATI", "INFORMATION ", "WIKI", "LOGGA UT"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drawerArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            getSupportFragmentManager().popBackStackImmediate();
                        }
                        mDrawerLayout.closeDrawers();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frame, new NyheterFragment())
                                .addToBackStack(null).commit();
                        mDrawerLayout.closeDrawers();
//                        notificationID = "6";
//                        notificationTitle = "SATANS DEMOKRATI - FÖREMÅL HITTAT";
//                        notificationText = "VILL DU SE?";
//                        getNotificationBuilder ();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frame, new InformationFragment())
                                .addToBackStack(null).commit();
                        mDrawerLayout.closeDrawers();
//                        notificationID = "5";
//                        notificationTitle = "SATANS DEMOKRATI - HÄNDELSE";
//                        notificationText = "ÖPPNA FÖR ATT DELTA";
//                        getNotificationBuilder ();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frame, new WikiFragment())
                                .addToBackStack(null).commit();
                        mDrawerLayout.closeDrawers();
//                        notificationID = "3";
//                        notificationTitle = "SATANS DEMOKRATI - HÄNDELSE";
//                        notificationText = "ÖPPNA FÖR ATT DELTA";
//                        getNotificationBuilder ();
                        break;
                    case 4:
                        LoginManager.getInstance().logOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        mDrawerLayout.closeDrawers();
                        break;
                }
            }
        });
    }

    /**
     * Called when user presses back
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * Sets up the navigation drawer
     */
    private void setUpDrawer() {
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
     * TODO: Förklara när den här metoden kallas.
     *
     * @param item
     * @return TODO: Vad är det som returneras? Drawern som blev tryckt på?
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void beaconHandler() {

        final BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        this.scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                if(!found.contains(result.getDevice().getAddress())) {
                    found.add(result.getDevice().getAddress());
                }
                switch (result.getDevice().getAddress()) {
                    case jZiggy:
                        if(!used.contains(jZiggy)) {
                            dbHandler.postIDToMessageDB(profileID);
                            notificationID = "2";
                            notificationTitle = "Woland har bjudit in till omröstning!";
                            notificationText = "Vill du delta?";
                            getNotificationBuilder ();
                            used.add(jZiggy);
                            assert used.contains(result.getDevice().getAddress());
                        }
                        break;
                    case jDonny:
                        if(!used.contains(jDonny)) {
                            dbHandler.postIDToMessageDB(profileID);
                        notificationID = "4";
                        notificationTitle = "SATANS DEMOKRATI - HÄNDELSE";
                        notificationText = "ÖPPNA FÖR ATT DELTA";
                        getNotificationBuilder ();
                            used.add(jDonny);
                            assert used.contains(result.getDevice().getAddress());
                        }
                        break;
                    case aZiggy:
                        if(!used.contains(aZiggy)) {
                            dbHandler.postIDToMessageDB(profileID);

                            used.add(jZiggy);
                            assert used.contains(result.getDevice().getAddress());
                        }
                        break;
                    case aDonny:
                        if(!used.contains(aDonny)) {
                            dbHandler.postIDToMessageDB(profileID);

                            used.add(jZiggy);
                            assert used.contains(result.getDevice().getAddress());
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        };
        bluetoothLeScanner.startScan(filterList, scanSettings, scanCallback);
    }

    private void uiStuff () {
        Animation in = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out);
        lampSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView lampView = new ImageView(MainActivity.this);
                return lampView;
            }
        });
        lampSwitcher.setImageResource(R.drawable.lamp_off);
        lampSwitcher.setInAnimation(in);
        lampSwitcher.setOutAnimation(out);

        beaconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!beaconMode) {
                    forestallningsDialog.setMessage(R.string.activateInfo);
                    forestallningsDialog();
                } else {
                    forestallningsDialog.setMessage("Är du säker på att du vill avbryta föreställnigsläge?");
                    forestallningsDialog();
                }
            }
        });
    }

    private void forestallningsDialog(){
        forestallningsDialog.setPositiveButton("JA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (!beaconMode) {
                    lampSwitcher.setImageResource(R.drawable.lamp_on);
                    beaconButton.setText("STÄNG AV FÖRESTÄLLNINGSLÄGE");
                    infoText.setText(R.string.showinfooff);
                    beaconMode = true;
//                    notificationID = "1";
//                    notificationTitle = "Woland har bjudit in till omröstning!";
//                    notificationText = "Vill du delta?";
//                    getNotificationBuilder ();
                    //messageHandler.lookForMessage();
                    //messageHandler.longTimer();
                    beaconHandler();
                } else {
                    lampSwitcher.setImageResource(R.drawable.lamp_off);
                    beaconButton.setText("AKTIVERA FÖRESTÄLLNINGSLÄGE");
                    infoText.setText(R.string.showinfoon);
                    beaconMode = false;
//                    notificationID = "2";
//                    notificationTitle = "Woland känner att något är fel";
//                    notificationText = "Man kanske skulle göra sig av med någon?";
//                    getNotificationBuilder ();
                    //messageHandler.stopSearch();
                }
            }
        });

        forestallningsDialog.setNegativeButton("NEJ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        forestallningsDialog.show();
    }

    private void getNotificationBuilder () {
        notificationIntent = new Intent(this, NotificationActivity.class);
        notificationIntent.putExtra("key", notificationID);
        pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setSmallIcon(android.R.drawable.stat_notify_chat).setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.summary_bw))
                .setAutoCancel(true).setPriority(Notification.PRIORITY_MAX).setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentTitle(notificationTitle).setContentText(notificationText);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}