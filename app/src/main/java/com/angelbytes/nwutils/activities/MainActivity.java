
/*
 * -----------------------------------
 * Droid Network Utils
 * -----------------------------------
 *
 * This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * ---------------------------------------------------------------------------
 * Started By: Navjot Singh <weavebytes@gmail.com> in March 2017
 *
 * Organization:Weavebytes Infotech Pvt Ltd
 * ---------------------------------------------------------------------------
 */

package com.angelbytes.nwutils.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.angelbytes.nwutils.config.MsgConfig;
import com.angelbytes.nwutils.R;
import com.angelbytes.nwutils.receiver.UDPreceiver;
import com.angelbytes.nwutils.sender.UDPsender;
import com.revmob.RevMob;
import com.revmob.ads.banner.RevMobBanner;

/**
 * This is the first screen or main screen showing to choose option for sender or receiver.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * UI Variables
     **/
    Button sender;
    Button receiver;
    Context context;

    /*** Revmob add**/
    RevMob revMob;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** initializing components **/
        initialization();
    }

    /**
     * handling on click events
     **/
    @Override
    public void onClick(View v) {

        MsgConfig.Okane(this, revMob);

        switch (v.getId()) {

            case R.id.btn_sender:
                Intent in = new Intent(MainActivity.this, UDPsender.class);
                startActivity(in);
                break;

            case R.id.btn_rec:
                Intent it = new Intent(MainActivity.this, UDPreceiver.class);
                startActivity(it);

                MsgConfig.showLogs(TAG,"btn receiver");
                MsgConfig.showToast(context,"btn receiver");

                break;
        }

    }//onClick

    public void initialization() {

        context = MainActivity.this;
        sender = (Button) findViewById(R.id.btn_sender);
        receiver = (Button) findViewById(R.id.btn_rec);
        sender.setOnClickListener(this);
        receiver.setOnClickListener(this);

        revMob = RevMob.start(this);
        RevMobBanner banner = revMob.createBanner(this);
        ViewGroup view = (ViewGroup) findViewById(R.id.banner);
        view.addView(banner);
    }

}//MainActivity
