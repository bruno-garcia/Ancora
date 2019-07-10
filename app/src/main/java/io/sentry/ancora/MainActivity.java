package io.sentry.ancora;
import android.content.Context;
import android.widget.TextView;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("greetings");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context ctx = this.getApplicationContext();
        String sentryDsn = "https://5fd7a6cda8444965bade9ccfd3df9882@sentry.io/1188141";
        Sentry.init(sentryDsn, new AndroidSentryClientFactory(ctx));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    unsafeMethod();
                } catch (Exception e) {
                    Sentry.capture(e);
                }

                // Native crash:
//                RustGreetings g = new RustGreetings();
//                Snackbar.make(view, g.sayHello("panic"), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    void unsafeMethod() {
        throw new UnsupportedOperationException("You shouldn't call this!");
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
}
