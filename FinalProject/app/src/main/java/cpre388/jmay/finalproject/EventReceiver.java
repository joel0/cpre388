package cpre388.jmay.finalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class EventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Boot Completed", Toast.LENGTH_SHORT).show();
        if (PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("start_with_os", false)) {
            Intent startIntent = new Intent(context, RelayService.class);
            context.startService(startIntent);
        }
    }
}
