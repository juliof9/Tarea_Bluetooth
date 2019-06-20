/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.mundo.UI.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;

import com.example.android.bluetoothchat.R;
import com.example.mundo.UI.Load.Load;
import com.example.mundo.UI.fragments.FragmentLoad;
import com.example.mundo.UI.Load.Wrapper;
import com.example.mundo.UI.Load.LoadOneMessage;
import com.example.mundo.UI.fragments.ChatFragment;

public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";

    // Whether the Load Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ChatFragment fragment = new ChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        Wrapper wrapper = new Wrapper();
        // Using Load, front-end to the logging chain, emulates android.util.log method signatures.
        Load.setLogNode(wrapper);

        // Filter strips out everything except the message text.
        LoadOneMessage msgFilter = new LoadOneMessage();
        wrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        FragmentLoad fragmentLoad = (FragmentLoad) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(fragmentLoad.getLogView());

        Load.i(TAG, "Ready");
    }
}
