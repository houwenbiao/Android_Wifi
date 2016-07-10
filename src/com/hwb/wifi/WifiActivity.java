package com.hwb.wifi;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WifiActivity extends Activity
{
	
	public WifiManager wifiManager = null;
	TextView tvWifiState = null,tvAllNet = null;
	private WifiInfo wifiInfo; 
	private List<ScanResult> mwifiList;
	private StringBuffer sb=new StringBuffer(); 
	// ɨ�����б�    
    private List<ScanResult> list;
    private ScanResult mScanResult;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		wifiManager = (WifiManager) WifiActivity.this.getSystemService(Context.WIFI_SERVICE);//��ȡwifiManager
		wifiInfo = wifiManager.getConnectionInfo();
		Button startWifi = (Button) findViewById(R.id.btn_startwifi);
		Button stopWifi = (Button) findViewById(R.id.btn_stopwifi);
		Button checkWifiState = (Button) findViewById(R.id.btn_wifistate);
		Button scanWifi = (Button) findViewById(R.id.btn_scanwifi);
		tvWifiState = (TextView) findViewById(R.id.tv_wifiState);
		tvAllNet = (TextView) findViewById(R.id.tv_allNet);
		startWifi.setOnClickListener(new startWifiListenner());
		stopWifi.setOnClickListener(new stopWifiListenner());
		checkWifiState.setOnClickListener(new checkWifiStateListener());
		scanWifi.setOnClickListener(new scanWifiListener());
		tvWifiState.setText("MAC��ַ��"+this.getMacAddress()+"\n"+"IP:"+Formatter.formatIpAddress(this.getIpAddress()));
		
	}
	public void startScan()
	{  
		wifiManager.startScan();  
        //�õ�ɨ����  
		mwifiList=wifiManager.getScanResults(); 
    }  
    //�õ������б�  
    public List<ScanResult> getWifiList()
    {  
        return mwifiList;  
    }
	
	public void getAllNetWorkList()
	{  
        // ÿ�ε��ɨ��֮ǰ�����һ�ε�ɨ����    
      if(sb!=null)
      {  
          sb=new StringBuffer();  
      }  
      //��ʼɨ������  
      this.startScan();  
      list=this.getWifiList();  
      if(list!=null)
      {  
          for(int i=0;i<list.size();i++)
          {  
              //�õ�ɨ����  
              mScanResult=list.get(i);  
              sb=sb.append(mScanResult.BSSID+"  ").append(mScanResult.SSID+"   ")  
              .append(mScanResult.capabilities+"   ").append(mScanResult.frequency+"   ")  
              .append(mScanResult.level+"\n\n");  
          }  
          tvAllNet.setText("ɨ�赽��wifi���磺\n"+sb.toString());  
      }  
  }
	
	
	public String getMacAddress()
	{  
        return wifiInfo.getMacAddress();  
    }
	public int getIpAddress()
	{  
        return wifiInfo.getIpAddress();  
    } 
	class scanWifiListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			getAllNetWorkList();
		}
		
	}
	
	class startWifiListenner implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			wifiManager.setWifiEnabled(true);//����wifi
			Toast.makeText(WifiActivity.this, "����wifi�ɹ�", Toast.LENGTH_SHORT).show();
		}
	}
	
	class stopWifiListenner implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			wifiManager.setWifiEnabled(false);//�ر�wifi
			Toast.makeText(WifiActivity.this, "wifi�رճɹ�", Toast.LENGTH_SHORT).show();
		}
	}
	
	class checkWifiStateListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			switch (wifiManager.getWifiState())
			{
				case WifiManager.WIFI_STATE_DISABLED:
					 Toast.makeText(WifiActivity.this, "wifi�ѹر�", Toast.LENGTH_SHORT).show();
					break;
				case WifiManager.WIFI_STATE_DISABLING:
					 Toast.makeText(WifiActivity.this, "wifi�ر���", Toast.LENGTH_SHORT).show();
					break;
				case WifiManager.WIFI_STATE_ENABLED:
					 Toast.makeText(WifiActivity.this, "wifi�ѿ���", Toast.LENGTH_SHORT).show();
					break;
				case WifiManager.WIFI_STATE_ENABLING:
					 Toast.makeText(WifiActivity.this, "wifi���ڿ���", Toast.LENGTH_SHORT).show();
					break;
				case WifiManager.WIFI_STATE_UNKNOWN:
					 Toast.makeText(WifiActivity.this, "δ֪wifi״̬", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
