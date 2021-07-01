package com.google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;

  private Video playingVideo;
  private boolean playingVideoPaused;

  private List<VideoPlaylist> playlists;


  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.playingVideo = null;
    this.playingVideoPaused = false;
    this.playlists = new ArrayList<VideoPlaylist>();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {

    //sort videos
    List<Video> videos = videoLibrary.getVideos();
    Collections.sort(videos);

    System.out.println("Here's a list of all available videos:");
    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {
      Video video = videos.get(i);
      System.out.println(
        "  " + video.stringify()
      );
    }
  }

  public void playVideo(String videoId) {

    if (videoLibrary.getVideo(videoId) == null) {
      System.out.println("Cannot play video: Video does not exist");
      return;
    }


    Video video = videoLibrary.getVideo(videoId);

    if (video.isFlagged()) {
      System.out.println("Cannot play video: Video is currently flagged (reason: " + video.getFlagReason() + ")");
      return;
    }

    if (playingVideo != null) {
      stopVideo();
    } 

    playingVideo = video;
    playingVideoPaused = false;

    System.out.println("Playing video: " + playingVideo.getTitle());

  }

  public void stopVideo() {
    if (playingVideo == null) {
      System.out.println("Cannot stop video: No video is currently playing");
    } else {
      System.out.println("Stopping video: " + playingVideo.getTitle());
      playingVideo = null;
      playingVideoPaused = false;
    }
  }

  public void playRandomVideo() {

    List<Video> validVideo = new ArrayList<Video>();

    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {
      Video video = videoLibrary.getVideos().get(i);

      if (video.isFlagged() == false) {
        validVideo.add(video);
      }
    }

    if (validVideo.size() == 0) {
      System.out.println("No videos available");
    } else {
      int randomIndex = ThreadLocalRandom.current().nextInt(0, validVideo.size());
      playVideo(validVideo.get(randomIndex).getVideoId());
    }

  }

  public void pauseVideo() {
    if (playingVideoPaused == true) {

      System.out.println("Video already paused: " + playingVideo.getTitle());

    } else {

      if (playingVideo != null) {
        playingVideoPaused = true;
        System.out.println("Pausing video: " + playingVideo.getTitle());
      } else {
        System.out.println("Cannot pause video: No video is currently playing");
      }

    }
  }

  public void continueVideo() {
    if (playingVideoPaused == true) {

      System.out.println("Continuing video: " + playingVideo.getTitle());
      playingVideoPaused = false;

    } else {

      if (playingVideo != null) {
        System.out.println("Cannot continue video: Video is not paused");
      } else {
        System.out.println("Cannot continue video: No video is currently playing");
      }

    }
  }

  public void showPlaying() {
    if (playingVideo != null) {
      if (playingVideoPaused) {
        System.out.println("Currently playing: " + playingVideo.stringify() + " - PAUSED");
      } else {
        System.out.println("Currently playing: " + playingVideo.stringify());
      }
    } else {
      System.out.println("No video is currently playing");
    }
  }




  private VideoPlaylist getVideoPlaylist(String playlistName) {
    for (int i = 0; i < playlists.size(); i++) {
      if (playlists.get(i).name.equalsIgnoreCase(playlistName)) {
        return playlists.get(i);
      }
    }

    return null;
  }

  public void createPlaylist(String playlistName) {
    
    if (playlistName != playlistName.replaceAll(" ", "_")) {
      System.out.println("Playlist name should have no spaces. You could try " + playlistName.replaceAll(" ", "_"));
      return;
    }

    if (getVideoPlaylist(playlistName) != null) {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
      return;
    }

    playlists.add(new VideoPlaylist(playlistName));
    System.out.println("Successfully created new playlist: " + playlistName);

  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    
    VideoPlaylist playlist = getVideoPlaylist(playlistName);
    if (playlist == null) {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
      return;
    }

    Video video = videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      return;      
    }

    if (video.isFlagged()) {
      System.out.println("Cannot add video to " + playlistName + ": Video is currently flagged (reason: " + video.getFlagReason() + ")");
      return;
    }


    if (playlist.addVideo(video) == true) {
      System.out.println("Added video to " + playlistName + ": " + video.getTitle());
    } else {
      System.out.println("Cannot add video to " + playlistName + ": Video already added");
    }

  }

  public void showAllPlaylists() {
    if (playlists.size() == 0) {
      System.out.println("No playlists exist yet");
      return;
    }

    //sort playlists
    List<VideoPlaylist> sortedPlaylists = playlists;
    Collections.sort(sortedPlaylists);

    System.out.println("Showing all playlists:");
    for (int i = 0; i < playlists.size(); i++) {
      System.out.println(
        "  " + playlists.get(i).getName()
      );
    }
  }

  public void showPlaylist(String playlistName) {

    VideoPlaylist playlist = getVideoPlaylist(playlistName);
    if (playlist == null) {
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
      return;
    }

    System.out.println("Showing playlist: " + playlistName);
    if (playlist.videos.size() == 0) {
      System.out.println("  " + "No videos here yet");
      return;
    }

    for (int i = 0; i < playlist.videos.size(); i++) {
      System.out.println("  " + playlist.videos.get(i).stringify());
    }

  }

  public void removeFromPlaylist(String playlistName, String videoId) {

    VideoPlaylist playlist = getVideoPlaylist(playlistName);
    if (playlist == null) {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
      return;
    }

    Video video = videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
      return;      
    }    

    if (playlist.removeVideo(video) == true) {
      System.out.println("Removed video from " + playlistName + ": " + video.getTitle());
    } else {
      System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
    }

  }

  public void clearPlaylist(String playlistName) {

    VideoPlaylist playlist = getVideoPlaylist(playlistName);
    if (playlist == null) {
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
      return;
    }

    for (int i = 0; i < playlist.videos.size(); i++) {
      playlist.removeVideo(playlist.videos.get(i));
    }
    System.out.println("Successfully removed all videos from " + playlistName);

  }

  public void deletePlaylist(String playlistName) {

    for (int i = 0; i < playlists.size(); i++) {
      if (playlists.get(i).getName().equalsIgnoreCase(playlistName)) {
        playlists.remove(i);
        System.out.println("Deleted playlist: " + playlistName);
        return;
      }
    }

    System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");

  }






  private void showSearchResults(List<Video> foundVideos, String searchTerm) {

    if (foundVideos.size() == 0) {
      System.out.println("No search results for " + searchTerm);
      return;
    }

    //*sort valid videos and print
    Collections.sort(foundVideos);
    System.out.println("Here are the results for " + searchTerm + ":");
    for (int j = 0; j < foundVideos.size(); j++) {
      Video video = foundVideos.get(j);
      System.out.println("  " + (j+1) + ") " + video.stringify());
    }

    //* request video to play
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, we will assume it's a no.");

    Scanner in = new Scanner(System.in);
    if (in.hasNextInt()) {
      int requestedVideo = in.nextInt();
      for (int k = 0; k < foundVideos.size(); k++) {
        if (requestedVideo == (k+1)) {
          playVideo(foundVideos.get(k).getVideoId());
          return;
        }
      }
    }

  }

  public void searchVideos(String searchTerm) {

    List<Video> foundVideos = new ArrayList<Video>();

    //* find valid videos
    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {
      Video video = videoLibrary.getVideos().get(i);

      if ((video.getTitle().toUpperCase().contains(searchTerm.toUpperCase())) & (video.isFlagged() == false)) {
        foundVideos.add(video);
      }
    }

    showSearchResults(foundVideos, searchTerm);

  }

  public void searchVideosWithTag(String videoTag) {

    List<Video> foundVideos = new ArrayList<Video>();

    if (videoTag.substring(0,1).toString().matches("#")) { //needs to start with a hashtag.
      //* find valid videos
      for (int i = 0; i < videoLibrary.getVideos().size(); i++) {
        Video video = videoLibrary.getVideos().get(i);

        if ((video.getTags().toString().toUpperCase().contains(videoTag.toUpperCase())) & (video.isFlagged() == false)) {
          foundVideos.add(video);
        }
      }
    }

    showSearchResults(foundVideos, videoTag);

  }







  public void flagVideo(String videoId) {
    flagVideo(videoId, "Not supplied");
  }

  public void flagVideo(String videoId, String reason) {
  
    Video video = videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot flag video: Video does not exist");
      return;      
    }  

    if (video.flag(reason) == true) {
      
      if (playingVideo == video) {
        stopVideo();
      }
      System.out.println("Successfully flagged video: " + video.getTitle() + " (reason: " + reason + ")");

    } else {
      System.out.println("Cannot flag video: Video is already flagged");
    }

  }

  public void allowVideo(String videoId) {
  
    Video video = videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot remove flag from video: Video does not exist");
      return;      
    }  

    if (video.clearFlag() == true) {
      System.out.println("Successfully removed flag from video: " + video.getTitle());
    } else {
      System.out.println("Cannot remove flag from video: Video is not flagged");
    }

  }


}