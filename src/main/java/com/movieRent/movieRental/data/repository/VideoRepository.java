package com.movieRent.movieRental.data.repository;

import com.movieRent.movieRental.data.model.Video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository  extends PagingAndSortingRepository<Video, Integer> {
    Page<Video> findVideoByVideoTitle(String videoTitle, Pageable firstPageWithTwoElements);
}
