package com.videoplayer.videoplayer;

public class MediaFiles {

    private String id;
    private String title;
    private String displayName;
    private String size;
    private String duration;
    private String path;
    private String dataAdded;

    public MediaFiles(String id, String title, String displayName, String size, String duration, String path, String dataAdded) {
        this.id = id;
        this.title = title;
        this.displayName = displayName;
        this.size = size;
        this.duration = duration;
        this.path = path;
        this.dataAdded = dataAdded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDataAdded() {
        return dataAdded;
    }

    public void setDataAdded(String dataAdded) {
        this.dataAdded = dataAdded;
    }
}
