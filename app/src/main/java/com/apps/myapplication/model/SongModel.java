package com.apps.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SongModel {

    @SerializedName("Songs")
    @Expose
    private List<Song> songs = null;

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
    public class Song {

        @SerializedName("song_name")
        @Expose
        private String songName;
        @SerializedName("song_path")
        @Expose
        private String songPath;
        @SerializedName("song_img")
        @Expose
        private String songImg;

        public String getSongName() {
            return songName;
        }

        public void setSongName(String songName) {
            this.songName = songName;
        }

        public String getSongPath() {
            return songPath;
        }

        public void setSongPath(String songPath) {
            this.songPath = songPath;
        }

        public String getSongImg() {
            return songImg;
        }

        public void setSongImg(String songImg) {
            this.songImg = songImg;
        }

    }


}
