package ru.test.sportsnewstestapplication.models.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by khrapachev on 04.09.2018.
 */

public class CategoryResponse {
    @SerializedName("events")
    @Expose
    private List<Event> events = null;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public class Event {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("coefficient")
        @Expose
        private String coefficient;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("place")
        @Expose
        private String place;
        @SerializedName("preview")
        @Expose
        private String preview;
        @SerializedName("article")
        @Expose
        private String article;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoefficient() {
            return coefficient;
        }

        public void setCoefficient(String coefficient) {
            this.coefficient = coefficient;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public String getArticle() {
            return article;
        }

        public void setArticle(String article) {
            this.article = article;
        }

    }
}
