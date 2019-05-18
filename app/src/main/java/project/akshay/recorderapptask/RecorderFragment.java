package project.akshay.recorderapptask;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

public class RecorderFragment extends Fragment {

    FloatingActionButton recordButton;
    ViewGroup viewGroup;
    TextView recordingText;
    Chronometer chronometer;
    String outputFile;
    MediaRecorder mediaRecorder;
    PreferenceManager preferenceManager;
    String recordingName;
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView =  inflater.inflate(R.layout.fragment_recorder,container,false);

        preferenceManager = new PreferenceManager(getContext());

        relativeLayout = rootView.findViewById(R.id.relativeLayout);
        recordButton = rootView.findViewById(R.id.recordButton);
        viewGroup = rootView.findViewById(R.id.linearLayout);
        recordingText = rootView.findViewById(R.id.recordingText);
        chronometer = rootView.findViewById(R.id.chronometer);

        setUpMediaRecorder();

        recordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!AppUtilities.isPlaying){

                    TransitionManager.beginDelayedTransition(viewGroup);
                    AppUtilities.isRecording = !AppUtilities.isRecording;
                    recordingText.setVisibility(AppUtilities.isRecording ? View.VISIBLE : View.GONE);

                    if(AppUtilities.isRecording) {
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IllegalStateException | IOException ise) {
                            AppUtilities.printLogMessages("Error",ise.getMessage());
                        }

                    } else {
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.stop();

                        Recording recording = new Recording(recordingName,outputFile);
                        AppUtilities.recordingsList.add(recording);
                        ListFragment.adapter.notifyDataSetChanged();

                        mediaRecorder.stop();
                        mediaRecorder.release();
                        setUpMediaRecorder();

                    }

                } else {
                    Snackbar.make(relativeLayout,rootView.getResources().getString(R.string.errorRecording),Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;

    }

    private void setUpMediaRecorder() {

        recordingName = generateRecordingName();
        outputFile = Environment.getExternalStorageDirectory()+"/Recordings/"+ recordingName;

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(outputFile);

    }

    private String generateRecordingName() {

        String recordingName = "recording";
        int recordingNumber = preferenceManager.getRecordingNumber();

        preferenceManager.setRecordingNumber(recordingNumber+1);

        return recordingName+String.valueOf(recordingNumber)+".3gp";

    }
}
