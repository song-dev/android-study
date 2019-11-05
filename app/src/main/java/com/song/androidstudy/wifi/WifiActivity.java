package com.song.androidstudy.wifi;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.androidstudy.R;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WifiActivity extends AppCompatActivity {

    private static final String TAG = "WifiActivity";

    private WifiManager wifiManager;
    private WiFiDataAdapter adapter;

    @BindView(R.id.recycler_wifi)
    RecyclerView recyclerView;
    private BluetoothManager bluetoothManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        ButterKnife.bind(this);

        int permission = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        wifiManager = ((WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bluetoothManager = ((BluetoothManager) this.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE));
        }

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//        registerReceiver(receiver, intentFilter);

        adapter = new WiFiDataAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        unregisterReceiver(receiver);
    }

    @OnClick(R.id.btn_scan_wifi)
    public void scanWiFi() {

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.e(TAG, "scanWiFi getBSSID: " + wifiInfo.getBSSID());
        Log.e(TAG, "scanWiFi getMacAddress: " + wifiInfo.getMacAddress());
        Log.e(TAG, "scanWiFi getSSID: " + wifiInfo.getSSID());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e(TAG, "scanWiFi getFrequency: " + wifiInfo.getFrequency());
        }
        Log.e(TAG, "scanWiFi getHiddenSSID: " + wifiInfo.getHiddenSSID());
        Log.e(TAG, "scanWiFi getIpAddress: " + wifiInfo.getIpAddress());
        Log.e(TAG, "scanWiFi getLinkSpeed: " + wifiInfo.getLinkSpeed());
        Log.e(TAG, "scanWiFi getNetworkId: " + wifiInfo.getNetworkId());
        Log.e(TAG, "scanWiFi getRssi: " + wifiInfo.getRssi());
        Log.e(TAG, "scanWiFi getSupplicantState: " + wifiInfo.getSupplicantState().name());

//        wifiManager.startScan();
        executeScanWiFi();

    }

    @OnClick(R.id.btn_scan_bluetooth)
    public void scanBluetooth() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            BluetoothAdapter adapter = bluetoothManager.getAdapter();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                BluetoothLeScanner bluetoothLeScanner = adapter.getBluetoothLeScanner();

                bluetoothLeScanner.startScan(new ScanCallback() {
                    @Override
                    public void onScanResult(int callbackType, android.bluetooth.le.ScanResult result) {
                        super.onScanResult(callbackType, result);

                        BluetoothDevice device = result.getDevice();
                        if (TextUtils.isEmpty(device.getName())||"null".equals(device.getName())){
                            final BleAdvertisedData badata = BleUtil.parseAdertisedData(null);
                            Log.e(TAG, "onScanResult getName: " + result.getScanRecord().getDeviceName());
                        }else {
                            Log.e(TAG, "onScanResult getName: " + device.getName());
                        }

                        Log.e(TAG, "onScanResult getAddress: " + device.getAddress());
                        Log.e(TAG, "onScanResult getBondState: " + device.getBondState());
                        Log.e(TAG, "onScanResult getType: " + device.getType());
                        Log.e(TAG, "onScanResult getUuids: " + Arrays.toString(device.getUuids()));
                    }

                    @Override
                    public void onBatchScanResults(List<android.bluetooth.le.ScanResult> results) {
                        super.onBatchScanResults(results);

                        for (android.bluetooth.le.ScanResult result : results) {
                            BluetoothDevice device = result.getDevice();
                            Log.e(TAG, "onBatchScanResults getName: " + device.getName());
                            Log.e(TAG, "onBatchScanResults getAddress: " + device.getAddress());
                            Log.e(TAG, "onBatchScanResults getBondState: " + device.getBondState());
                            Log.e(TAG, "onBatchScanResults getType: " + device.getType());
                            Log.e(TAG, "onBatchScanResults getUuids: " + Arrays.toString(device.getUuids()));
                        }
                    }

                    @Override
                    public void onScanFailed(int errorCode) {
                        super.onScanFailed(errorCode);
                        Log.e(TAG, "onScanFailed: " + errorCode);
                    }
                });

            } else {
                adapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
                    @Override
                    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

                    }
                });
            }
        }

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                // wifi已成功扫描到可用wifi。
                executeScanWiFi();

            }
        }

    };

    private class WiFiDataAdapter extends RecyclerView.Adapter<WiFiDataHolder> {


        private Context context;
        private List<ScanResult> scanResults;

        public WiFiDataAdapter(Context context) {
            this.context = context;
        }

        public void setScanResults(List<ScanResult> scanResults) {
            this.scanResults = scanResults;
        }

        @NonNull
        @Override
        public WiFiDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_wifi, parent, false);
            WiFiDataHolder wiFiDataHolder = new WiFiDataHolder(view);
            return wiFiDataHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull WiFiDataHolder holder, int position) {

            ScanResult scanResult = scanResults.get(position);

            StringBuffer sb = new StringBuffer();
            sb.append("\n设备名字:\t" + scanResult.SSID);
            sb.append("\n地址:\t" + scanResult.BSSID);
            sb.append("\n加密方式:\t" + scanResult.capabilities);
            sb.append("\n接入频率:\t" + scanResult.frequency);
            sb.append("\n信号强度\t" + scanResult.level + "\n");
            holder.content.setText(sb.toString());

        }

        @Override
        public int getItemCount() {
            return scanResults == null ? 0 : scanResults.size();
        }
    }

    class WiFiDataHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.content)
        protected TextView content;

        public WiFiDataHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void executeScanWiFi() {

        List<ScanResult> scanResults = wifiManager.getScanResults();
        scanResults = filterScanResult(scanResults);

        adapter.setScanResults(scanResults);
        adapter.notifyDataSetChanged();

        for (ScanResult result : scanResults) {
            Log.e(TAG, "onReceive: =======start======");

            Log.e(TAG, "onReceive SSID: " + result.SSID);
            Log.e(TAG, "onReceive BSSID: " + result.BSSID);
            Log.e(TAG, "onReceive capabilities: " + result.capabilities);
            Log.e(TAG, "onReceive frequency: " + result.frequency);
            Log.e(TAG, "onReceive level: " + result.level);
            Log.e(TAG, "onReceive timestamp: " + result.timestamp);
            Log.e(TAG, "onReceive describeContents: " + result.describeContents());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.e(TAG, "onReceive centerFreq0: " + result.centerFreq0);
                Log.e(TAG, "onReceive centerFreq1: " + result.centerFreq1);
                Log.e(TAG, "onReceive channelWidth: " + result.channelWidth);
                Log.e(TAG, "onReceive operatorFriendlyName: " + result.operatorFriendlyName);
                Log.e(TAG, "onReceive venueName: " + result.venueName);
                Log.e(TAG, "onReceive is80211mcResponder: " + result.is80211mcResponder());
                Log.e(TAG, "onReceive isPasspointNetwork: " + result.isPasspointNetwork());
            }
            Log.e(TAG, "onReceive: =======end======");
        }
    }

    /**
     * 以 SSID 为关键字，过滤掉信号弱的选项
     *
     * @param list
     * @return
     */
    public static List<ScanResult> filterScanResult(final List<ScanResult> list) {
        LinkedHashMap<String, ScanResult> linkedMap = new LinkedHashMap<>(list.size());
        for (ScanResult rst : list) {
            if (linkedMap.containsKey(rst.SSID)) {
                if (rst.level > linkedMap.get(rst.SSID).level) {
                    linkedMap.put(rst.SSID, rst);
                }
                continue;
            }
            linkedMap.put(rst.SSID, rst);
        }
        list.clear();
        list.addAll(linkedMap.values());
        return list;
    }
}
