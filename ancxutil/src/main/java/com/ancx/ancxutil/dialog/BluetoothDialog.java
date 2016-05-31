package com.ancx.ancxutil.dialog;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ancx.ancxutil.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 蓝牙连接Dialog
 * Created by Ancx on 16/3/17.
 */
public class BluetoothDialog extends Dialog implements AdapterView.OnItemClickListener {

    private List<BluetoothDevice> mData = new ArrayList<>();

    public BluetoothDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    private ListView mListView;
    private BluetoothAdapter mAdapter;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bluetooth);
        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new BluetoothAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    public void scanComplete() {
        if (tv_title != null)
            tv_title.setText("扫描完成");
    }

    public void startScan() {
        if (tv_title != null)
            tv_title.setText("正在扫描中。。。");
    }

    private class BluetoothAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getContext(), R.layout.item_dialog_bluetooth, null);
            TextView tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            tv_item.setText(mData.get(position).getName());
            return convertView;
        }
    }

    public void addBluetooth(BluetoothDevice device) {
        mData.add(device);
        mAdapter.notifyDataSetChanged();
    }

    public void onlyAddBluetooth(BluetoothDevice device) {
        mData.add(device);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        if (onBluetoothSelectListener != null) {
            onBluetoothSelectListener.onSelect(mData.get(position));
        }
    }

    public interface OnBluetoothSelectListener {
        void onSelect(BluetoothDevice device);
    }

    private OnBluetoothSelectListener onBluetoothSelectListener;

    public void setOnBluetoothSelectListener(OnBluetoothSelectListener onBluetoothSelectListener) {
        this.onBluetoothSelectListener = onBluetoothSelectListener;
    }

    public void clearBluetooth() {
        if (mAdapter != null) {
            mData.clear();
            mAdapter.notifyDataSetChanged();
        }
    }
}
