package project.akshay.recorderapptask;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

public class RecorderFragment extends Fragment {

    FloatingActionButton recordButton;
    ViewGroup viewGroup;
    TextView recordingText;
    Chronometer chronometer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_recorder,container,false);

        recordButton = rootView.findViewById(R.id.recordButton);
        viewGroup = rootView.findViewById(R.id.linearLayout);
        recordingText = rootView.findViewById(R.id.recordingText);
        chronometer = rootView.findViewById(R.id.chronometer);

        recordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(viewGroup);
                AppUtilities.isRecording = !AppUtilities.isRecording;
                recordingText.setVisibility(AppUtilities.isRecording ? View.VISIBLE : View.GONE);

                if(AppUtilities.isRecording) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                } else {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.stop();
                }

            }
        });

        return rootView;

    }
}
