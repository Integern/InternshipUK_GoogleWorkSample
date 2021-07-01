package com.google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import java.util.*;


/** A class used to represent a Playlist */
class VideoPlaylist implements java.lang.Comparable<VideoPlaylist> {

    public String name;
    public List<Video> videos;


    public VideoPlaylist() {
        this.name = "unnamed";
        this.videos = new ArrayList<Video>();
    }

    public VideoPlaylist(String givenName) {
        this.name = givenName;
        this.videos = new ArrayList<Video>();
    }


    public String getName() {
        return name;
    }


    public boolean hasVideo(Video video) {
        for (int i = 0; i < videos.size(); i++) {
            if (videos.get(i) == video) {
                return true;
            }
        }
        return false;
    }

    public boolean addVideo(Video video) {
        if (hasVideo(video) == false) {
            videos.add(video);
            return true;
        }
        return false;
    }

    public boolean removeVideo(Video video) {
        for (int i = 0; i < videos.size(); i++) {
            if (videos.get(i) == video) {
                videos.remove(i);
                return true;
            }
        }

        return false;
    }


/**Compares lexigraphically */
  public int compareTo(VideoPlaylist playlist) {
    return (this.name).compareTo(playlist.name);
  }

}
