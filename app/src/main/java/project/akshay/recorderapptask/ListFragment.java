package project.akshay.recorderapptask;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class ListFragment extends Fragment {

    String path;
    RecyclerView recordingsListView;
    LinearLayoutManager manager;
    static RecordingsAdapter adapter;
    Recording recording;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_list,container,false);

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

        adapter.setOnRecordingClickListener(new OnRecordingClickListener() {
            @Override
            public void onRecordingsClick(LinearLayout linearLayout, int position, TextView recordingStatus) {

                TransitionManager.beginDelayedTransition(linearLayout);
                AppUtilities.isPlaying = !AppUtilities.isPlaying;
                recordingStatus.setVisibility(AppUtilities.isPlaying ? View.VISIBLE : View.GONE);

            }
        });

        recordingsListView.setLayoutManager(manager);
        recordingsListView.setAdapter(adapter);

        return rootView;
    }

    public interface OnRecordingClickListener {

        void onRecordingsClick(LinearLayout linearLayout, int position, TextView recordingStatus);

    }

}
