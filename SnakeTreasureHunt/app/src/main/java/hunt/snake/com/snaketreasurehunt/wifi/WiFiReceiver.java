package hunt.snake.com.snaketreasurehunt.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.util.Log;


/**
 * A BroadcastReceiver that notifies of important wifi p2p events.
 */
public class WiFiReceiver extends BroadcastReceiver {

	private WifiP2pManager manager;
	private Channel channel;
    private DeviceListFragment fragment;


	public WiFiReceiver(WifiP2pManager manager, Channel channel, DeviceListFragment fragment) {
		super();
		this.manager = manager;
		this.channel = channel;
        this.fragment = fragment;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
        System.out.println("onReceive");
		String action = intent.getAction();
        System.out.println("Action: " + action);

		if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {

			// UI update to indicate wifi p2p status.
			int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

			if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
				// Wifi Direct mode is enabled
			} else {
				fragment.clearPeers();
			}
			Log.d("DEBUG", "P2P state changed - " + state);

		} else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

			// request available peers from the wifi p2p manager
			if (manager != null) {
				manager.requestPeers(channel, fragment);
			}

			Log.d("DEBUG", "P2P peers changed");

		} else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

			if (manager == null) {
				return;
			}

            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {
                Log.d("DEBUG", "Device is connected");

                WifiP2pInfo info = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_INFO);

                if(info.isGroupOwner) {
                    fragment.createServer();
                } else {
                    fragment.createClient(info.groupOwnerAddress.getHostAddress());
                }
			} else {
				// It's a disconnect
				fragment.clearPeers();
			}
		} else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
			fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));

		}
	}
}
