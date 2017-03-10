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

package com.angelbytes.nwutils.sender;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.angelbytes.nwutils.R;
import com.angelbytes.nwutils.config.MsgConfig;
import com.revmob.RevMob;
import com.revmob.ads.banner.RevMobBanner;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This is the UDP Sender which is used to send the messages.
 */
public class UDPsender extends AppCompatActivity implements View.OnClickListener {

    /**
     * UI Variables
     **/
    TextView textResponse;
    EditText editTextAddress;
    EditText editTextPort;
    Button buttonConnect;
    EditText welcomeMsg;
    Context context;

    /**
     * Revmob add
     **/
    RevMob revMob;
    private static final String TAG = "UDPsender";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udpsender);

        /** initializing components **/
        initialization();

        buttonConnect.setOnClickListener(this);
    }


    @Override
    public void onClick(View arg0) {

        MsgConfig.Okane(this, revMob);
        String tMsg = welcomeMsg.getText().toString();
        if (tMsg.equals("")) {
            tMsg = null;
            MsgConfig.showToast(context, "No Welcome Msg sent");
        }

        MyClientTask myClientTask = new MyClientTask(editTextAddress
                .getText().toString(), Integer.parseInt(editTextPort
                .getText().toString()),
                tMsg);
        myClientTask.execute();
    }

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response = "";
        String msgToServer;

        MyClientTask(String addr, int port, String msgTo) {
            dstAddress = addr;
            dstPort = port;
            msgToServer = msgTo;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;

            try {
                socket = new Socket(dstAddress, dstPort);
                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                if (msgToServer != null) {
                    dataOutputStream.writeUTF(msgToServer);
                }

                response = dataInputStream.readUTF();

            } catch (UnknownHostException e) {
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textResponse.setText(response);
            super.onPostExecute(result);
        }
    }


    public void initialization() {

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);
        textResponse = (TextView) findViewById(R.id.response);
        welcomeMsg = (EditText) findViewById(R.id.welcomemsg);

        revMob = RevMob.start(this);
        RevMobBanner banner = revMob.createBanner(this);
        ViewGroup view = (ViewGroup) findViewById(R.id.banner1);
        view.addView(banner);
    }
}
