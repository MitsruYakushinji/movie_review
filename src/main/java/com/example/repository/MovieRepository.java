package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	public List<Movie> findByDeletedAtIsNull();
}
