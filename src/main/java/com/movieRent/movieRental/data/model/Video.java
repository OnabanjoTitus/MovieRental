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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer videoId;
    @Column
    private String videoTitle;
    @Column
    private String videoType;
    @Column
    private Genre videoGenre;
}
