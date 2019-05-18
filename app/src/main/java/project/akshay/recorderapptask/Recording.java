package project.akshay.recorderapptask;

public class Recording {

    public String recordingName, recordingPath;

    public Recording(String recordingName, String recordingPath) {
        this.recordingName = recordingName;
        this.recordingPath = recordingPath;
    }

    public String getRecordingName() {
        return recordingName;
    }

    public String getRecordingPath() {
        return recordingPath;
    }
}
