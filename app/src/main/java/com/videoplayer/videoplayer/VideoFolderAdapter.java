package com.videoplayer.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoFolderAdapter extends RecyclerView.Adapter<VideoFolderAdapter.ViewHolder> {
    private final ArrayList<MediaFiles> mediaFiles;
    private final ArrayList<String> folderPath;
    private final Context context;

    public VideoFolderAdapter(ArrayList<MediaFiles> mediaFiles, ArrayList<String> folderPath, Context context) {
        this.mediaFiles = mediaFiles;
        this.folderPath = folderPath;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.folder_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//          /storage/Media/Videos

        int indexPath = folderPath.get(position).lastIndexOf("/");
        String nameOfFolder = folderPath.get(position).substring(indexPath+1);
        holder.folderName.setText(nameOfFolder);
        holder.folder_path.setText(folderPath.get(position));
        holder.noOfFiles.setText("5 Videos");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,VideoFilesActivity.class);
                intent.putExtra("folderName",nameOfFolder);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderPath.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView folderName,folder_path,noOfFiles;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            folderName = itemView.findViewById(R.id.folderNameId);
            folder_path = itemView.findViewById(R.id.folderPathId);
            noOfFiles = itemView.findViewById(R.id.noOfFilesId);

        }
    }
}
