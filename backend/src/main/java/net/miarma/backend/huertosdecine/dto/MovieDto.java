package net.miarma.backend.huertosdecine.dto;

import java.util.UUID;

public class MovieDto {
    public static class Request {
        private String title;
        private String description;
        private String cover;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }

    public static class Response {
        private UUID movieId;
        private String title;
        private String description;
        private String cover;
        private long upvotes;
        private long downvotes;
        private Byte userVote;

        public UUID getMovieId() {
            return movieId;
        }

        public void setMovieId(UUID movieId) {
            this.movieId = movieId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public long getUpvotes() {
            return upvotes;
        }

        public void setUpvotes(long upvotes) {
            this.upvotes = upvotes;
        }

        public long getDownvotes() {
            return downvotes;
        }

        public void setDownvotes(long downvotes) {
            this.downvotes = downvotes;
        }

        public Byte getUserVote() {
            return userVote;
        }

        public void setUserVote(Byte userVote) {
            this.userVote = userVote;
        }
    }
}
