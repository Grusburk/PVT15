package grupp2.satansdemocracy;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mattin on 2016-05-05.
 */
public class BeaconHandler extends Thread{
    private List<String> found = new ArrayList<>();
    private List<String> usedBeacon = new ArrayList<>();
    private List<ScanFilter> filterList;
    private ScanCallback scanCallback;
    private BluetoothLeScanner bluetoothLeScanner;
    private final String jZiggy = "CC:69:C6:5B:13:D7";
    private final String jDonny = "FF:FF:50:01:25:63";
    private final String aZiggy = "F2:E1:A3:7E:CF:BC";
    private final String aDonny = "FF:FF:70:01:4C:E6";
    private Context mContext;
    private BeaconListener listener;
    private Integer eventID;
    private ScanSettings scanSettings;
    private boolean running;
    private long timestart;

    public BeaconHandler (Context mContext) {
        this.mContext = mContext;
    }

    public void setListnener(BeaconListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        completeTask();
    }

    public void BeaconSetUp() {
        scanSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
                .build();
        boolean running = true;
        ScanFilter jZiggyFilter = new ScanFilter.Builder().setDeviceAddress(jZiggy).build();
        ScanFilter jDonnyFilter = new ScanFilter.Builder().setDeviceAddress(jDonny).build();
        ScanFilter aZiggyFilter = new ScanFilter.Builder().setDeviceAddress(aZiggy).build();
        ScanFilter aDonnyFilter = new ScanFilter.Builder().setDeviceAddress(aDonny).build();

        final BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        filterList = new ArrayList<>();
        filterList.add(jZiggyFilter);
        filterList.add(jDonnyFilter);
        filterList.add(aZiggyFilter);
        filterList.add(aDonnyFilter);
      while (running) {
            BeaconScanner(bluetoothAdapter);
        }
        completeTask();
    }

    private void BeaconScanner(BluetoothAdapter bluetoothAdapter) {
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        this.scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                if (!found.contains(result.getDevice().getAddress())) {
                    found.add(result.getDevice().getAddress());
                }
                switch (result.getDevice().getAddress()) {
                    case jZiggy:
                        if (!usedBeacon.contains(jZiggy)) {
                            eventID = 1;
                            listener.didRecieveBeaconEvent(eventID);
                            usedBeacon.add(jZiggy);
                            assert usedBeacon.contains(result.getDevice().getAddress());
                        }
                        break;
                    case jDonny:
                        if (!usedBeacon.contains(jDonny)) {
                            eventID = 2;
                            listener.didRecieveBeaconEvent(eventID);
                            usedBeacon.add(jDonny);
                            assert usedBeacon.contains(result.getDevice().getAddress());
                        }
                        break;
                    case aZiggy:
                        if (!usedBeacon.contains(aZiggy)) {
                            eventID = 3;
                            listener.didRecieveBeaconEvent(eventID);
                            usedBeacon.add(aZiggy);
                            assert usedBeacon.contains(result.getDevice().getAddress());
                        }
                        break;
                    case aDonny:
                        if (!usedBeacon.contains(aDonny)) {
                            eventID = 4;
                            listener.didRecieveBeaconEvent(eventID);
                            usedBeacon.add(aDonny);
                            assert usedBeacon.contains(result.getDevice().getAddress());
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

    public void stopSearch() {
        running = false;
    }

    public boolean completeTask() {
        try {
            this.sleep(5 * 1000);
            Log.i("HJAAJHHAHAst", "5 seccccccc");
        } catch (InterruptedException e) {
            running = false;
            e.printStackTrace();
        }
        long timeran = System.currentTimeMillis()-timestart;
        if(timeran > 4*1000*60*60)
            running = false;
        return true;
    }
}
