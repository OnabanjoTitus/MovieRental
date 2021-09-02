package com.movieRent.movieRental.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String videoId;
    @Column(nullable = false)
    private String videoTitle;
    @Column(nullable = false)
    private Object videoType;
    @Column(nullable = false)
    private Genre videoGenre;
}
