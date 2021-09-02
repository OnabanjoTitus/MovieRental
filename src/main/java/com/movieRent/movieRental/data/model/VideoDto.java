package com.movieRent.movieRental.data.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
public class VideoDto {
    private String videoTitle;
    private Object videoType;
    private Genre videoGenre;
}
