package com.example.camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Uri videoUri;
    private int CAMERA_CAPTURE;
    Button btn;
    ImageView img;
    VideoView vid;
    Button btnVid;

    public void PlaybackRecordedVideo(){
        vid.setVideoURI(videoUri);
        vid.setMediaController(new MediaController(this));
        vid.requestFocus();
        vid.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnTakePhoto);
        img = findViewById(R.id.img);
        vid = findViewById(R.id.vid);
        btnVid = findViewById(R.id.btnVideo);
        btn.setOnClickListener(view -> {
            try {
                CAMERA_CAPTURE = 1;
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            }
            catch (ActivityNotFoundException cant){
                String errorMessage = "Камера не поддерживается вашим устройством";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        btnVid.setOnClickListener(view -> {
            try{
                CAMERA_CAPTURE = 2;
                Intent captureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            } catch (ActivityNotFoundException cant){
                String errorMessage = "Камера не поддерживается вашим устройством";
                Toast toast = Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2){
            assert data != null;
            Uri video = data.getData();
            vid.setVideoURI(video);
            vid.requestFocus();
            vid.start();
        }
        else
        {
            Bundle extras = data.getExtras();
            Bitmap thumbnailBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(thumbnailBitmap);
        }
    }
}