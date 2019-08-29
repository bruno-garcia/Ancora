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
import io.sentry.event.EventBuilder;
import io.sentry.event.interfaces.ExceptionInterface;
import io.sentry.event.interfaces.ExceptionMechanism;
import io.sentry.event.interfaces.ExceptionMechanismThrowable;

public class MainActivity extends AppCompatActivity {

//    static {
//        System.loadLibrary("greetings");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context ctx = this.getApplicationContext();
//        String sentryDsn = "https://46fee3fb0e2a45cca85f2f2c41efe52c@sentry.io/1379099";
        String sentryDsn = "https://46fee3fb0e2a45cca85f2f2c41efe52c@sentry.io/1379099?" +
                "stacktrace.app.packages=io.sentry.ancora&" +
                "anr.enable=true&" +
                "anr.timeoutIntervalMs=1000";

        Sentry.init(sentryDsn, new AndroidSentryClientFactory(ctx));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Native crash:
                RustGreetings g = new RustGreetings();
                Snackbar.make(view, g.sayHello("panic"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fob = findViewById(R.id.fob);
        fob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    unsafeMethod();
                } catch (Exception e) {
                    Sentry.capture(e);
                }
            }
        });

        FloatingActionButton bla = findViewById(R.id.bla);
        bla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Try cause ANR (expect default 1 sec for debugging)
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return;
                }
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
