package com.movieRent.movieRental.data.repository;

import com.movieRent.movieRental.data.model.Video;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface VideoRepository  extends PagingAndSortingRepository<Video, Integer> {

}
