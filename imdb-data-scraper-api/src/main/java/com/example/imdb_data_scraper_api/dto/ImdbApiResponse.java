package com.example.imdb_data_scraper_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Add this annotation to ignore unknown properties
public class ImdbApiResponse {
    @JsonProperty("@meta")
    private Meta meta;

    @JsonProperty("results")
    private List<Result> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {
        private String operation;
        private String requestId;
        private double serviceTimeMs;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        private String id;
        private Image image;
        @JsonProperty("runningTimeInMinutes")
        private int runningTimeInMinutes;
        private String title;
        private String titleType;
        private int year;
        private List<Principal> principals;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Image {
            private int height;
            private String id;
            private String url;
            private int width;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Principal {
            private String id;
            private String legacyNameText;
            private String name;
            private int billing;
            private String category;
            private List<String> characters;
            private List<Role> roles;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Role {
                private String character;
                private String characterId;
            }
        }
    }
}
