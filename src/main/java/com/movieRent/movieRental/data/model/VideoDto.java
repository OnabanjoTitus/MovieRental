package com.movieRent.movieRental.data.model;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class VideoDto extends RepresentationModel<VideoDto> {
    private String videoTitle;
    private String videoType;
    private String videoGenre;

}
