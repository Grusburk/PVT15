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

public class BeaconHandler extends Thread {
    private List<String> found = new ArrayList<>();
    private List<String> usedBeacon = new ArrayList<>();
    private List<ScanFilter> filterList;
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

    BeaconHandler(Context mContext) {
        this.mContext = mContext;
    }

    void setListener(BeaconListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        completeTask();
    }

    void BeaconSetUp() {
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
//        while (running) {
            BeaconScanner(bluetoothAdapter);
//        }
        completeTask();
    }

    private void BeaconScanner(BluetoothAdapter bluetoothAdapter) {
        BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        ScanCallback scanCallback = new ScanCallback() {
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
                            listener.didReceiveBeaconEvent(eventID);
                            usedBeacon.add(jZiggy);
                            if (!usedBeacon.contains(result.getDevice().getAddress())) throw new AssertionError();
                        }
                        break;
                    case jDonny:
                        if (!usedBeacon.contains(jDonny)) {
                            eventID = 2;
                            listener.didReceiveBeaconEvent(eventID);
                            usedBeacon.add(jDonny);
                            if (!usedBeacon.contains(result.getDevice().getAddress())) throw new AssertionError();
                        }
                        break;
                    case aZiggy:
                        if (!usedBeacon.contains(aZiggy)) {
                            eventID = 3;
                            listener.didReceiveBeaconEvent(eventID);
                            usedBeacon.add(aZiggy);
                            if (!usedBeacon.contains(result.getDevice().getAddress())) throw new AssertionError();
                        }
                        break;
                    case aDonny:
                        if (!usedBeacon.contains(aDonny)) {
                            eventID = 4;
                            listener.didReceiveBeaconEvent(eventID);
                            usedBeacon.add(aDonny);
                            if (!usedBeacon.contains(result.getDevice().getAddress())) throw new AssertionError();
                        }
                        break;
                    default:
                        break;
                }//Receive
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

    void stopSearch() {
        running = false;
    }

    private boolean completeTask() {
        try {
            sleep(5000);
            Log.i("HJAAJHHAHAst", "5 seccccccc");
        } catch (InterruptedException e) {
            running = false;
            e.printStackTrace();
        }
        long timeRan = System.currentTimeMillis() - timestart;
        if (timeRan > 4 * 1000 * 60 * 60)
            running = false;
        return true;
    }
}
