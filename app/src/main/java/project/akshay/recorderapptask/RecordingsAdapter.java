package project.akshay.recorderapptask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordingsAdapter extends RecyclerView.Adapter<RecordingsAdapter.MyViewHolder> {

    private ListFragment.OnRecordingClickListener onRecordingClickListener;

    public void setOnRecordingClickListener(ListFragment.OnRecordingClickListener onRecordingClickListener) {
        this.onRecordingClickListener = onRecordingClickListener;
    }

    ArrayList<Recording> recordingsList;

    public RecordingsAdapter(ArrayList<Recording> recordingsList) {
        this.recordingsList = recordingsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_recordings,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final int index = i;

        myViewHolder.recordingName.setText(recordingsList.get(i).getRecordingName());
        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecordingClickListener.onRecordingsClick(myViewHolder.linearLayout,index,myViewHolder.recordingStatus);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recordingsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView recordingName;
        TextView recordingStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linearLayout);
            recordingName = itemView.findViewById(R.id.recordingName);
            recordingStatus = itemView.findViewById(R.id.recordingStatus);

        }
    }
}
