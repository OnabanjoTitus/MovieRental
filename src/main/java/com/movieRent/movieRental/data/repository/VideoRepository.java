package com.movieRent.movieRental.data.repository;

import com.movieRent.movieRental.data.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video,Integer> {
}
