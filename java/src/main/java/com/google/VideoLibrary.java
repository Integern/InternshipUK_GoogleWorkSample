package com.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary {

  private final HashMap<String, Video> videos;

  VideoLibrary() {
    this.videos = new HashMap<>();

    // Wasn't able to find the file.. so did this janky method.
    Scanner scanner = new Scanner(
      "Funny Dogs | funny_dogs_video_id |  #dog , #animal"
      + "\n" + "Amazing Cats | amazing_cats_video_id |  #cat , #animal"
      + "\n" + "Another Cat Video | another_cat_video_id |  #cat , #animal"
      + "\n" + "Life at Google | life_at_google_video_id |  #google , #career"
      + "\n" + "Video about nothing | nothing_video_id |"
      );

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] split = line.split("\\|");
      String title = split[0].strip();
      String id = split[1].strip();
      List<String> tags;
      if (split.length > 2) {
        tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(
            Collectors.toList());
      } else {
        tags = new ArrayList<>();
      }
      this.videos.put(id, new Video(title, id, tags));
    }
    scanner.close();
  }

  List<Video> getVideos() {
    return new ArrayList<>(this.videos.values());
  }

  /**
   * Get a video by id. Returns null if the video is not found.
   */
  Video getVideo(String videoId) {
    return this.videos.get(videoId);
  }
}
