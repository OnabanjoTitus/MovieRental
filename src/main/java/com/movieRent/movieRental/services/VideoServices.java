package com.movieRent.movieRental.services;

import com.movieRent.movieRental.data.model.Video;
import com.movieRent.movieRental.data.model.VideoDto;
import com.movieRent.movieRental.web.Exception.VideoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VideoServices {
    List<VideoDto> findAllVideos();
    VideoDto addVideo(VideoDto videoDto) throws VideoException;
}
