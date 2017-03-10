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

package com.angelbytes.nwutils.config;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.revmob.RevMob;


public class MsgConfig {

    public static int Clickcount = 0;

    public static boolean debugMode = false; // set to false while release

    public static void Okane(Activity activity, RevMob revMob) {
        Clickcount++;
        if (Clickcount == 6) {
            revMob.showFullscreen(activity);
            Clickcount = 0;
        }
    }

    public static void showLogs(String TAG, String message) {
        if (debugMode)
            Log.e(TAG, "-----------" + message);
    }

    public static void showToast(Context context, String message) {
        if (debugMode)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
