package hunt.snake.com.snaketreasurehunt.wifi;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hunt.snake.com.snaketreasurehunt.R;
import hunt.snake.com.snaketreasurehunt.SnakeTreasureHuntGame;

/**
 * A ListFragment that displays available peers on discovery and requests the
 * parent activity to handle user interaction events
 */
public class DeviceListFragment extends ListFragment implements PeerListListener, ChannelListener, DeviceActionListener{
    private static final String EXTRA_CLIENT = "EXTRA_CLIENT";

	private ProgressDialog progressDialog = null;
	private View mContentView;

    private boolean retryChannel;

    private IntentFilter intentFilter;

    private WiFiReceiver receiver;
    private WifiP2pManager.Channel channel;
    private WifiP2pManager manager;
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    private Server server;
    private WifiP2pDevice device;

    private Button btnStart;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        setListAdapter(new WiFiPeerListAdapter(getActivity(), R.layout.fragment_row_devices, peers));
		mContentView = inflater.inflate(R.layout.fragment_choose_game, null);

        init();

		return mContentView;
	}

    private void init() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getActivity().getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(getActivity(), getActivity().getMainLooper(), null);

        manager.cancelConnect(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Disconnected!");
            }

            @Override
            public void onFailure(int i) {
                System.out.println("Not disconnected!");
            }
        });
        initButtons();
    }

    private void initButtons() {
        Button choose = (Button) mContentView.findViewById(R.id.btnSearch);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createReceiver();
                onInitiateDiscovery();
                //if(!isWifiP2pEnabled) Toast.makeText(getActivity(), "WiFi Direct not enabled. You need to turn it on first.", Toast.LENGTH_SHORT).show();
                makeAvailableToConnect();
            }
        });

        btnStart = (Button) mContentView.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientService.setIsHost(true);
                Intent intent = new Intent(getActivity(), SnakeTreasureHuntGame.class);
                intent.putExtra("isHost", true);
                startActivity(intent);
                clientService.sendMessage("message sent");
                //clientService.test();
            }
        });
        btnStart.setEnabled(false);
    }


    private ClientService clientService;

    public void createClient(final String serverAddress) {
        System.out.println("in init Client");
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                System.out.println("In service connected");
                ClientService.ClientBinder binder = (ClientService.ClientBinder) service;
                clientService = binder.getClient();
                clientService.setCurrentActivity(getActivity());
                System.out.println("Clientservice: " + clientService);
                clientService.initClient(serverAddress);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        Intent intent = new Intent(getActivity(), ClientService.class);
        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public WiFiReceiver getWifiReceiver() {
        return receiver;
    }

    public IntentFilter getIntentFilter() {
        return intentFilter;
    }

    public void createServer() {
        if(server == null) {
            server = new Server();
            while(!server.isStarted) {
                System.out.println("...wait for server to start...");
            }
            System.out.println("Server started...");
            createClient(server.serverAddress);
            btnStart.setEnabled(true);
        }
    }

    private void makeAvailableToConnect() {
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "Getting ready!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reasonCode) {
                Toast.makeText(getActivity(), "Failed: " + reasonCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createReceiver() {
        if(receiver == null) {
            receiver = new WiFiReceiver(manager, channel, DeviceListFragment.this);
            getActivity().registerReceiver(receiver, intentFilter);
        }
    }

	/**
	 * @return this device
	 */
	public WifiP2pDevice getDevice() {
		return device;
	}

	private static String getDeviceStatus(int deviceStatus) {
		Log.d("DEBUG", "Peer status :" + deviceStatus);

		switch (deviceStatus) {
            case WifiP2pDevice.AVAILABLE:
                return "Available";
            case WifiP2pDevice.INVITED:
                return "Invited";
            case WifiP2pDevice.CONNECTED:
                return "Connected";
            case WifiP2pDevice.FAILED:
                return "Failed";
            case WifiP2pDevice.UNAVAILABLE:
                return "Unavailable";
            default:
                return "Unknown";
		}
	}

	/**
	 * Initiate a connection with the peer.
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		WifiP2pDevice device = (WifiP2pDevice) getListAdapter().getItem(
				position);
		WifiP2pConfig config = new WifiP2pConfig();
		config.deviceAddress = device.deviceAddress;
		this.connect(config, device.deviceName);

	}

    @Override
    public void onChannelDisconnected() {
        if (manager != null && !retryChannel) {
            Toast.makeText(getActivity(), "Channel lost. Trying again",Toast.LENGTH_LONG).show();
            clearPeers();
            retryChannel = true;
            manager.initialize(getActivity(), getActivity().getMainLooper(), this);
        } else {
            Toast.makeText(getActivity(),
                    "Severe! Channel is probably lost premanently. Try Disable/Re-Enable P2P.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showDetails(WifiP2pDevice device) {

    }

    @Override
    public void cancelDisconnect() {
/*
		 * A cancel abort request by user. Disconnect i.e. removeGroup if
		 * already connected. Else, request WifiP2pManager to abort the ongoing
		 * request
		 */
        if (manager != null) {
            if (getDevice() == null
                    || getDevice().status == WifiP2pDevice.CONNECTED) {
                disconnect();
            } else if (getDevice().status == WifiP2pDevice.AVAILABLE
                    || getDevice().status == WifiP2pDevice.INVITED) {

                manager.cancelConnect(channel, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(getActivity(),
                                "Aborting connection", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        Toast.makeText(getActivity(),
                                "Connect abort request failed. Reason Code: "
                                        + reasonCode, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        }
    }

    @Override
    public void connect(final WifiP2pConfig config, final String deviceName) {
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "Connecting to " + deviceName + "...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(getActivity(), "Connecting failed. Retry.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void disconnect() {
        manager.removeGroup(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onFailure(int reasonCode) {
                Log.d("SNAKE", "Disconnect failed. Reason :" + reasonCode);

            }

            @Override
            public void onSuccess() {
            }

        });
    }

    /**
	 * Array adapter for ListFragment that maintains WifiP2pDevice list.
	 */
	private class WiFiPeerListAdapter extends ArrayAdapter<WifiP2pDevice> {

		private List<WifiP2pDevice> items;

		/**
		 * @param context
		 * @param textViewResourceId
		 * @param objects
		 */
		public WiFiPeerListAdapter(Context context, int textViewResourceId, List<WifiP2pDevice> objects) {
			super(context, textViewResourceId, objects);
			items = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

            System.out.println("getView");
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.fragment_row_devices, null);
			}

			WifiP2pDevice device = items.get(position);

			if (device != null) {
				TextView top = (TextView) v.findViewById(R.id.device_name);
				TextView bottom = (TextView) v.findViewById(R.id.device_details);

				if (top != null) {
					top.setText(device.deviceName);
				}

				if (bottom != null) {
					bottom.setText(getDeviceStatus(device.status));
				}

                int color;
                switch (device.status) {
                    case WifiP2pDevice.CONNECTED:
                        color = Color.parseColor("#00CC00");
                        break;
                    case WifiP2pDevice.INVITED:
                        color = Color.parseColor("#FF6600");
                        break;
                    default:
                        color = Color.parseColor("#FFFFFF");
                        break;
                }
                top.setTextColor(color);
                bottom.setTextColor(color);
			}

			return v;
		}
	}

	/**
	 * Update UI for this device.
	 * 
	 * @param device
	 *            WifiP2pDevice object
	 */
	public void updateThisDevice(WifiP2pDevice device) {

	}

	@Override
	public void onPeersAvailable(WifiP2pDeviceList peerList) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}

        System.out.println("Peers: " + peerList.getDeviceList());


		peers.clear();
		peers.addAll(peerList.getDeviceList());

		((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();

		if (peers.size() == 0) {
			Log.d("DEBUG", "No devices found");
			return;
		}

	}

	public void clearPeers() {
		peers.clear();
		((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
	}

	/**
     * 
     */
	public void onInitiateDiscovery() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		progressDialog = ProgressDialog.show(getActivity(), "Press back to cancel", "finding peers", true, true,
				new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {

					}
				});
	}
}
