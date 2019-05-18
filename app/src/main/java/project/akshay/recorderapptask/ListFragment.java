package project.akshay.recorderapptask;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ListFragment extends Fragment {

    String path;
    RecyclerView recordingsListView;
    LinearLayoutManager manager;
    static RecordingsAdapter adapter;
    Recording recording;
    MediaPlayer mediaPlayer;
    int currentPosition = Integer.MAX_VALUE;
    TextView previousTextView;
    LinearLayout previousLinearLayout;
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView =  inflater.inflate(R.layout.fragment_list,container,false);

        relativeLayout = rootView.findViewById(R.id.relativeLayout);

        path = Environment.getExternalStorageDirectory()+"/Recordings";
        File directory = new File(path);
        File[] files = directory.listFiles();

        recordingsListView = rootView.findViewById(R.id.recordingsListView);
        manager = new LinearLayoutManager(getContext());

        AppUtilities.recordingsList.clear();

        for (File file : files) {
            recording = new Recording(file.getName(), file.getAbsolutePath());
            AppUtilities.recordingsList.add(recording);
        }

        adapter = new RecordingsAdapter(AppUtilities.recordingsList);
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.reset();
                TransitionManager.beginDelayedTransition(previousLinearLayout);
                previousTextView.setVisibility(View.GONE);
                AppUtilities.isPlaying = !AppUtilities.isPlaying;
                currentPosition = Integer.MAX_VALUE;
                previousTextView = null;
                previousLinearLayout = null;
            }
        });

        adapter.setOnRecordingClickListener(new OnRecordingClickListener() {
            @Override
            public void onRecordingsClick(LinearLayout linearLayout, int position, TextView recordingStatus) {

                if(!AppUtilities.isRecording){

                    previousLinearLayout = linearLayout;
                    TransitionManager.beginDelayedTransition(linearLayout);

                    if(currentPosition != position){

                        try {
                            previousTextView.setVisibility(View.GONE);
                            AppUtilities.isPlaying = !AppUtilities.isPlaying;
                        } catch (Exception e){
                            AppUtilities.printLogMessages("ERROR",e.getMessage());
                        }

                        previousTextView = recordingStatus;
                        AppUtilities.isPlaying = !AppUtilities.isPlaying;
                        previousTextView.setVisibility(AppUtilities.isPlaying ? View.VISIBLE : View.GONE);

                        try {
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(AppUtilities.recordingsList.get(position).getRecordingPath());
                            mediaPlayer.prepare();
                            currentPosition = position;
                        } catch (IOException e) {
                            AppUtilities.printLogMessages("ERROR",e.getMessage());
                        }

                        mediaPlayer.start();

                    } else {

                        if (AppUtilities.isPlaying) {
                            mediaPlayer.pause();
                        } else {
                            mediaPlayer.start();
                        }

                        AppUtilities.isPlaying = !AppUtilities.isPlaying;
                        previousTextView.setVisibility(AppUtilities.isPlaying ? View.VISIBLE : View.GONE);

                    }

                } else {

                    Snackbar.make(relativeLayout,rootView.getResources().getString(R.string.errorPlaying),Snackbar.LENGTH_SHORT).show();

                }

            }
        });

        recordingsListView.setLayoutManager(manager);
        recordingsListView.setAdapter(adapter);

        Toast.makeText(getContext(),rootView.getResources().getString(R.string.instructions),Toast.LENGTH_SHORT).show();

        return rootView;
    }

    public interface OnRecordingClickListener {

        void onRecordingsClick(LinearLayout linearLayout, int position, TextView recordingStatus);

    }



}
