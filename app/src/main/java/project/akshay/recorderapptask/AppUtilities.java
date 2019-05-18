package project.akshay.recorderapptask;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

public class AppUtilities {

    public static boolean isRecording = false;

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    private static void replaceFont(String staticTypefaceFieldName,
                                    final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printLogMessages(String tag, String message){
        Log.d(tag,message);
    }


}