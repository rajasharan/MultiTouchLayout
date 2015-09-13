package com.rajasharan.multitouchdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.rajasharan.layout.MultiTouchLayout;

public class MainActivity extends AppCompatActivity {
    private Menu mMenu;
    private MultiTouchLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = (MultiTouchLayout) findViewById(R.id.touch);

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("https://www.google.com");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (mLayout.isTouchEnabled()) {
            menu.findItem(R.id.action_toggle_on).setVisible(true);
            menu.findItem(R.id.action_toggle_off).setVisible(false);
        }
        else {
            menu.findItem(R.id.action_toggle_on).setVisible(false);
            menu.findItem(R.id.action_toggle_off).setVisible(true);
        }
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_toggle_on) {
            item.setVisible(false);
            mMenu.findItem(R.id.action_toggle_off).setVisible(true);
            mLayout.setTouchesEnabled(false);
            Toast.makeText(this, "Touches Disabled", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_toggle_off) {
            item.setVisible(false);
            mMenu.findItem(R.id.action_toggle_on).setVisible(true);
            mLayout.setTouchesEnabled(true);
            Toast.makeText(this, "Touches Enabled", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
