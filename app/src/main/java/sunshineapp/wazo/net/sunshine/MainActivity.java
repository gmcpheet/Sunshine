package sunshineapp.wazo.net.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements ForecastFragment.Callback
{
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private String mLocation;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mLocation = Utility.getPreferredLocation(this);

        Log.d(LOG_TAG,"onCreate method entered.");

        setContentView(R.layout.activity_main);

        if (findViewById(R.id.weather_detail_container) != null)
        {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null)
            {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.weather_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        }
        else
        {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);  // don't want to cast shadow
        }

        ForecastFragment forecastFragment = ((ForecastFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_forecast));
        forecastFragment.setUseTodayLayout(!mTwoPane);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(LOG_TAG,"OnPause method entered.");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(LOG_TAG,"OnResume method entered.");

        String location = Utility.getPreferredLocation( this );
        // update the location in our second pane using the fragment manager
        if (location != null && !location.equals(mLocation))
        {
            ForecastFragment ff =
                    (ForecastFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_forecast);

            if ( ff != null )
            {
                ff.onLocationChanged();
            }

            DetailFragment df =
                    (DetailFragment)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);

            if ( null != df )
            {
                df.onLocationChanged(location);
            }
            mLocation = location;
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(LOG_TAG,"OnStop method entered.");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(LOG_TAG,"OnStart method entered");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(LOG_TAG,"onDestroy method entered.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

                return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Uri contentUri)
    {
        if (mTwoPane)
        {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.DETAIL_URI, contentUri);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.weather_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        }
        else
        {
            Intent intent = new Intent(this, DetailActivity.class)
                    .setData(contentUri);
            startActivity(intent);
        }
    }
}
