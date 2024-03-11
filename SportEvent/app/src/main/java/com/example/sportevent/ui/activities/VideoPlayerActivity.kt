package com.example.sportevent.ui.activities

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.sportevent.R

class VideoPlayerActivity : AppCompatActivity() {

    companion object {
        const val VIDEO = "video"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        val videoView = findViewById<VideoView>(R.id.videoView)

        // Replace "YOUR_VIDEO_URL" with the actual URL of your video
        val videoUrl = intent.getStringExtra(VIDEO)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)

        val videoUri = Uri.parse(videoUrl)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(videoUri)

        videoView.requestFocus()

        videoView.setOnCompletionListener { finish() }

        videoView.start()
    }
}