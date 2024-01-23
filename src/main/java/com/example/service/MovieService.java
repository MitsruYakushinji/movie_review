package com.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Movie;
import com.example.form.MovieForm;
import com.example.repository.MovieRepository;

@Service
public class MovieService {
	private final MovieRepository movieRepository;

	@Autowired
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	// 全件検索
	public List<Movie> findAll() {
		return this.movieRepository.findAll();
	}

	// IDでの単体検索
	public Movie findById(Integer id) {
		Optional<Movie> optionalMovie = this.movieRepository.findById(id);
		Movie movie = optionalMovie.get();
		return movie;
	}

	// データの更新
	public Movie update(Integer id, MovieForm movieForm) {
		Movie movie = this.findById(id);
		movie.setTitle(movieForm.getTitle());
		movie.setScore(movieForm.getScore());
		// 更新日時も登録
		movie.setUpdateAt(LocalDateTime.now());

		return this.movieRepository.save(movie);
	}

	// データの削除(論理削除)
	public Movie delete(Integer id) {
		// idから該当のデータを取得
		Movie movie = this.findById(id);
		// deletedAtフィールドを上書き
		movie.setDeletedAt(LocalDateTime.now());
		
		return this.movieRepository.save(movie);
	}

	// deletedAtカラムがnullの情報を検索
	public List<Movie> findByDeletedAtIsNull() {
		return this.movieRepository.findByDeletedAtIsNull();
	}
}
