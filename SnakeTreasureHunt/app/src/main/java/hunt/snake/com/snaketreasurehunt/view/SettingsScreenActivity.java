package hunt.snake.com.snaketreasurehunt.view;


import android.app.Activity;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import hunt.snake.com.snaketreasurehunt.R;

public class SettingsScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
            MediaPlayer mediaPlayer;

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_settings_screen, container, false);

            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);


            ToggleButton s = (ToggleButton) rootView.findViewById(R.id.soundswitch);
            if(s != null) {
                s.setOnCheckedChangeListener(this);
            }


            ImageView avatar = (ImageView) rootView.findViewById(R.id.avatar);
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return rootView;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.ystory_intro);

            if(isChecked) {
                mediaPlayer.start();
            }
            else {
                mediaPlayer.pause();
            }

            mediaPlayer.release();

        }
    }
}
