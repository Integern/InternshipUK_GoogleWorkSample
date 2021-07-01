package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video implements java.lang.Comparable<Video> {

  private final String title;
  private final String videoId;
  private final List<String> tags;

  private String flagReason;
  private boolean flagged;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);

    this.flagged = false;
    this.flagReason = "";
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  String stringify() {
    String printyBoi = getTitle() + " (" + getVideoId() + ") " + getTags().toString().replaceAll(",","");
    if (flagged) {
      printyBoi += " - FLAGGED (reason: " + flagReason + ")";
    }
    return printyBoi;
  }

  boolean flag(String reason) {
    if (flagged == false) {
      flagged = true;
      flagReason = reason;
      return true;
    }

    return false;
  }

  boolean clearFlag() {
    if (flagged == true) {
      flagged = false;
      flagReason = "";
      return true;
    }

    return false;
  }

  boolean isFlagged() {
    return flagged;
  }

  String getFlagReason() {
    return flagReason;
  }


  /**Compares lexigraphically */
  public int compareTo(Video vid) {
    return (this.title).compareTo(vid.title);
  }
}
