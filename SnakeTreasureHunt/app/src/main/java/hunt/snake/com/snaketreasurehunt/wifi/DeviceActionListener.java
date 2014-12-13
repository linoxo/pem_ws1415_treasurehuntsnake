package hunt.snake.com.snaketreasurehunt.wifi;

import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;

/**
 * Created by Tom on 12/9/14.
 */
public interface DeviceActionListener {
    void showDetails(WifiP2pDevice device);

    void cancelDisconnect();

    void connect(WifiP2pConfig config, String deviceName);

    void disconnect();
}
