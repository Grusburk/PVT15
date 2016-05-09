package grupp2.satansdemocracy;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements MessageListener, BeaconListener{
    private TextView infoText, twitterTitel;
    private Intent notificationIntent;
    private PendingIntent pendingIntent;
    private Notification.Builder notificationBuilder;
    private AlertDialog.Builder popUpDialog, noBluetoothDialog;
    private Button beaconButton;
    private ImageSwitcher lampSwitcher;
    private ImageView twitter;
    private boolean beaconMode;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String notificationID = "1", notificationTitle, notificationText, profileID;
    private BluetoothAdapter bluetoothAdapter;
    private Toolbar toolbar;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private DBHandler dbHandler = new DBHandler();
    private MessageHandler messageHandler;
    private BeaconHandler beaconHandlerTest;
    /**
     * Sets up an instance of the mainActivity class upon first creation.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        twitter = (ImageView) toolbar.findViewById(R.id.twitterlogo);
        twitterTitel = (TextView) toolbar.findViewById(R.id.twittertitle);
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
        popUpDialog = new AlertDialog.Builder(MainActivity.this,android.R.style.Theme_Holo_Dialog_NoActionBar);
        noBluetoothDialog = new AlertDialog.Builder(MainActivity.this,android.R.style.Theme_Holo_Dialog_NoActionBar);
        messageHandler = new MessageHandler(profileID);
        messageHandler.setListnener(this);
        beaconHandlerTest = new BeaconHandler(this);
        beaconHandlerTest.setListnener(this);
        /**
         * Get facebook profile ID from LoginActivity
         */
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            profileID = extras.getString("facebookID");
        }
        /**
         * Request access for Beacon Searching
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
    }
}

    private void notificationSettings() {
        notificationBuilder.setSmallIcon(android.R.drawable.stat_notify_chat).setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.summary_bw)).setAutoCancel(true).setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE).setContentTitle(notificationTitle).setContentText(notificationText);
    }
    /**
     * Called for marshmallow access for permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        /** Empty method */
    }
    /**
     * Sets up the font for the application
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    /**
     * Creates an array for the navigation drawer in which to add headers.
     * Then using a switch statement to set up the headers.
     */
    private void addDrawerItems() {
        String[] drawerArray = {"FÖRESTÄLLNING", "BAKGRUND", "TWITTERFLÖDE", "KÖP BILJETT","OM OSS","LOGGA UT"};
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
                        toolbar.setTitle("SATANS DEMOKRATI");
                        updateToolbarName();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new WikiFragment()).addToBackStack(null).commit();
                        mDrawerLayout.closeDrawers();
                        toolbar.setTitle("BAKGRUND");
                        updateToolbarName();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new NyheterFragment()).addToBackStack(null).commit();
                        mDrawerLayout.closeDrawers();
                        twitterTitel.setVisibility(View.VISIBLE);
                        twitter.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        Intent ticketIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://kulturbiljetter.se/evenemang/satans-delirium-del-2-i-satans-trilogi-2269"));
                        startActivity(ticketIntent);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new InformationFragment()).addToBackStack(null).commit();
                        mDrawerLayout.closeDrawers();
                        toolbar.setTitle("OM OSS");
                        updateToolbarName();
                        break;
                    case 5:
                        LoginManager.getInstance().logOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        mDrawerLayout.closeDrawers();
                        break;
                }
            }
        });
    }

    private void updateToolbarName() {
        twitter.setVisibility(View.GONE);
        twitterTitel.setVisibility(View.GONE);
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
     * @param item
     * @return TODO: Vad är det som returneras? Drawern som blev tryckt på?
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void uiStuff () {
        lampSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView lampView = new ImageView(MainActivity.this);
                return lampView;
            }
        });
        lampSwitcher.setImageResource(R.drawable.lamp_off);
        lampSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        lampSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out));
        beaconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!beaconMode) {
                    popUpDialog.setMessage(R.string.activateInfo);
                    forestallningsDialog();
                } else {
                    popUpDialog.setMessage("Är du säker på att du vill avbryta föreställnigsläge?");
                    forestallningsDialog();
                }
            }
        });
    }

    private void forestallningsDialog(){
        popUpDialog.setPositiveButton("JA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (!beaconMode) {
                    if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
                        noBluetoothDialog.setMessage("DU MÅSTE AKTIVERA BLÅTAND")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }else{
                        lampSwitcher.setImageResource(R.drawable.lamp_on);
                        beaconButton.setText("STÄNG AV FÖRESTÄLLNINGSLÄGE");
                        infoText.setText(R.string.showinfooff);
                        beaconMode = true;
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                            messageHandler.lookForMessage();
                            }
                        });
                        beaconHandlerTest.BeaconSetUp();
                    }
                } else {
                    lampSwitcher.setImageResource(R.drawable.lamp_off);
                    beaconButton.setText("AKTIVERA FÖRESTÄLLNINGSLÄGE");
                    infoText.setText(R.string.showinfoon);
                    beaconMode = false;
                    messageHandler.stopSearch();
                    beaconHandlerTest.stopSearch();
                }
            }
        });
        popUpDialog.setNegativeButton("NEJ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        popUpDialog.show();
    }

    public void getNotificationBuilder () {
        notificationIntent = new Intent(this, NotificationActivity.class);
        notificationIntent.putExtra("key", notificationID);
        pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationSettings();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public void didRecieveEventID(int id) {
        switch (id) {
            case 1:
                notificationID = "1";
                notificationTitle = "Woland har bjudit in till omröstning!";
                notificationText = "Vill du delta?";
                getNotificationBuilder ();
                break;
            case 2:
                notificationID = "2";
                notificationTitle = "Woland känner att något är fel";
                notificationText = "Man kanske skulle göra sig av med någon?";
                getNotificationBuilder ();
                break;
            case 3:
                notificationID = "3";
                questionNotificationText();
                getNotificationBuilder ();
                break;
            case 4:
                notificationID = "4";
                questionNotificationText();
                getNotificationBuilder ();
                break;
            case 5:
                notificationID = "5";
                questionNotificationText();
                getNotificationBuilder ();
                break;
        }
    }

    private void questionNotificationText() {
        notificationTitle = "SATANS DEMOKRATI - HÄNDELSE";
        notificationText = "ÖPPNA FÖR ATT DELTA";
    }

    @Override
    public void didReceiveMessage(String message) {
        notificationTitle = "Viktigt meddelande";
        notificationText = "Nu måste du känna dig speciell :)?";
        notificationIntent = new Intent(this, NotificationActivity.class);
        notificationIntent.putExtra("special", message);
        pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationSettings();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public void didRecieveBeaconEvent(int id) {
        switch (id) {
            case 1:
                beaconEventNotification();
                break;
            case 2:
                beaconEventNotification();
                break;
            case 3:
                beaconEventNotification();
                break;
            case 4:
                beaconEventNotification();
                break;
        }
    }

    private void beaconEventNotification() {
        notificationID = "6";
        notificationTitle = "SATANS DEMOKRATI - FÖREMÅL HITTAT";
        notificationText = "VILL DU SE?";
        getNotificationBuilder ();
        dbHandler.postIDToMessageDB(profileID);
    }
}