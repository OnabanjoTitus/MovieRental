package com.movieRent.movieRental.data.model;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class VideoPrice extends RepresentationModel<VideoPrice> {
    private String videoTitle;
    private String videoType;
    private String videoGenre;
    private Double videoPrice;
}
