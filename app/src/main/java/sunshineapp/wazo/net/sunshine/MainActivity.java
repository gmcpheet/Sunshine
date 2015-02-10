package sunshineapp.wazo.net.sunshine;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ArrayAdapter<String> mForecastAdapter;

        public PlaceholderFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            String[] forecastArray = {
                    "Today - Sunny - 86/73",
                    "Tomorrow - Foggy - 70/40",
                    "Weds - Cloudy - 72/63",
                    "Thurs - Asteroids - 75/65",
                    "Fri - Heavy Rain - 65/56",
                    "Sat - HELP TRAPPED IN WEATHERSTATION - 60/51",
                    "Sun - Sunny - 80/73",
                    "Next Monday - Sunny - 80/60",
                    "Next Tuesday - Rainy - 80/66",
                    "Next Wednesday - Snow Flurries - 60/-3",
                    "Next Thursday - Clouds - 45/65",
                    "Next Friday - Sunny and Warm - 80/60",
                    "Next Saturday - Overcast - 70/55",
                    "Next Sunday - Mooncast - 65/55"
            };
            List<String> weekForecast = new ArrayList<String>(
                    Arrays.asList(forecastArray));

            // Now that we have fake data create an ArrayAdapter .
            // The ArrayAdapter will take data from a source - in this case
            // the weekForecast list and populate the listview it's
            // attached to.

            mForecastAdapter = new ArrayAdapter<String>(
                    // get the current context - this fragement's parent activity
                    getActivity(),
                    // ID of the list item layout
                    R.layout.list_item_forecast,
                    // ID of the textview to populate
                    R.id.list_item_forecast_textview,
                    // forecast data
                    weekForecast);

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            // get a reference to the ListView and then set this adapter to it
            ListView listView = (ListView) rootView.findViewById(
                    R.id.listview_forecast);

            listView.setAdapter(mForecastAdapter);

            return rootView;
        }
    }
}
