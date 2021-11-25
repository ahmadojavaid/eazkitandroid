package com.example.jbalpha.eazkitv8.Models;

public class AllSessionsModel {

    String session_id, user_id, sessionImage, sessionRating, timeCarriedOut, actualTimeCarriedOut;
    String Date;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public AllSessionsModel(String session_id, String user_id, String sessionImage, String sessionRating, String timeCarriedOut, String actualTimeCarriedOut) {
        this.session_id = session_id;
        this.user_id = user_id;
        this.sessionImage = sessionImage;
        this.sessionRating = sessionRating;
        this.timeCarriedOut = timeCarriedOut;
        this.actualTimeCarriedOut = actualTimeCarriedOut;
    }

    public AllSessionsModel() {
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSessionImage() {
        return sessionImage;
    }

    public void setSessionImage(String sessionImage) {
        this.sessionImage = sessionImage;
    }

    public String getSessionRating() {
        return sessionRating;
    }

    public void setSessionRating(String sessionRating) {
        this.sessionRating = sessionRating;
    }

    public String getTimeCarriedOut() {
        return timeCarriedOut;
    }

    public void setTimeCarriedOut(String timeCarriedOut) {
        this.timeCarriedOut = timeCarriedOut;
    }

    public String getActualTimeCarriedOut() {
        return actualTimeCarriedOut;
    }

    public void setActualTimeCarriedOut(String actualTimeCarriedOut) {
        this.actualTimeCarriedOut = actualTimeCarriedOut;
    }
}
