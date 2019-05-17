package project.akshay.recorderapptask;

import android.app.Application;

public class RecorderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppUtilities.setDefaultFont(this,"SERIF","fonts/Product-Sans-Bold.ttf");

    }
}
