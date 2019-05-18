package project.akshay.recorderapptask;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    final private String PREF_NAME = "RECORDING_NUMBER";
    final private int PRIVATE_MODE = 0;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    Context context;

    final private static String KEY_RECORDING_NUMBER = "KeyRecordingNumber";

    public PreferenceManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setRecordingNumber(int recordingNumber) {
        editor.putInt(KEY_RECORDING_NUMBER,recordingNumber);
        editor.apply();
    }

    public int getRecordingNumber() {

        return sharedPreferences.getInt(KEY_RECORDING_NUMBER,1);

    }

}
