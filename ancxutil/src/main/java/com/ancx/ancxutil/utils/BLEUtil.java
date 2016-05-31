package com.ancx.ancxutil.utils;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;

import com.ancx.ancxutil.App;

import java.util.ArrayList;
import java.util.List;

/**
 * BLE4.0蓝牙连接工具类
 * Created by Ancx
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BLEUtil {

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private Handler handler = new Handler();

    public void scan() {
        if (Integer.parseInt(Build.VERSION.SDK) < 18 && !App.getInstance().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            // 不支持BLE
            MsgUtil.ToastShort("您的手机不支持此功能！");
            return;
        }
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) App.getInstance().getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = mBluetoothManager.getAdapter();
        }
        if (mBluetoothAdapter == null) {
            MsgUtil.ToastShort("您的手机不支持此功能！");
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            // 蓝牙没有打开则打开
            if (mBluetoothAdapter.enable()) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scanLeDevice();
                    }
                }, 2000);
                return;
            }
        }
        // 蓝牙已经打开
        scanLeDevice();
    }

    public interface OnDeviceFindListener {
        void onDeviceFind(BluetoothDevice device);

        void onScanOver();
    }

    private OnDeviceFindListener onDeviceFindListener;

    public void setOnDeviceFindListener(OnDeviceFindListener onDeviceFindListener) {
        this.onDeviceFindListener = onDeviceFindListener;
    }

    private List<String> address = new ArrayList<>();
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (device != null && !address.contains(device.getAddress())) {
                address.add(device.getAddress());
                if (onDeviceFindListener != null)
                    onDeviceFindListener.onDeviceFind(device);
            }
        }
    };

    private boolean isScaning;

    private void scanLeDevice() {
        address.clear();
        if (isScaning)
            stopScan();
        mBluetoothAdapter.startLeScan(mLeScanCallback);
        isScaning = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopScan();
                if (onDeviceFindListener != null)
                    onDeviceFindListener.onScanOver();
            }
        }, 10000);
    }

    public void stopScan() {
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        isScaning = false;
    }

    public BluetoothGatt connect(BluetoothDevice device) {
        BluetoothGatt mBluetoothGatt = device.connectGatt(App.getInstance(), false, mGattCallback);
        stopScan();
        return mBluetoothGatt;
    }

    public BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (onBLEGattCallbackListener != null)
                onBLEGattCallbackListener.onConnectionStateChange(gatt, status, newState);
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (onBLEGattCallbackListener != null)
                onBLEGattCallbackListener.onServicesDiscovered(gatt, status);
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (onBLEGattCallbackListener != null)
                onBLEGattCallbackListener.onCharacteristicRead(gatt, characteristic, status);
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (onBLEGattCallbackListener != null)
                onBLEGattCallbackListener.onCharacteristicChanged(gatt, characteristic);
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (onBLEGattCallbackListener != null)
                onBLEGattCallbackListener.onCharacteristicWrite(gatt, characteristic, status);
        }
    };

    public interface OnBLEGattCallbackListener {
        /**
         * 手机和设备的连接状态监听
         *
         * @param gatt
         * @param status
         * @param newState
         */
        void onConnectionStateChange(BluetoothGatt gatt, int status, int newState);

        void onServicesDiscovered(BluetoothGatt gatt, int status);

        /**
         * BLE终端数据被读
         *
         * @param gatt
         * @param characteristic
         * @param status
         */
        void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status);

        /**
         * 特征反馈数据监听
         *
         * @param gatt
         * @param characteristic
         */
        void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic);

        /**
         * 特征被写入数据
         *
         * @param gatt
         * @param characteristic
         * @param status
         */
        void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status);
    }

    private OnBLEGattCallbackListener onBLEGattCallbackListener;

    public void setOnBLEGattCallbackListener(OnBLEGattCallbackListener onBLEGattCallbackListener) {
        this.onBLEGattCallbackListener = onBLEGattCallbackListener;
    }

    /**
     * 十六进制转换成字符串
     *
     * @param src
     * @return
     */
    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}