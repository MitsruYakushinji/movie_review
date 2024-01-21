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
	private final MovieRepository movieRepositoy;

	@Autowired
	public MovieService(MovieRepository movieRepositoy) {
		this.movieRepositoy = movieRepositoy;
	}

	// 全件検索
	public List<Movie> findAll() {
		return this.movieRepositoy.findAll();
	}

	// IDでの単体検索
	public Movie findById(Integer id) {
		Optional<Movie> optionalMovie = this.movieRepositoy.findById(id);
		Movie movie = optionalMovie.get();
		return movie;
	}
	
	// データの保存
	public Movie save(MovieForm movieForm) {
		Movie movie = new Movie();
		
		movie.setTitle(movieForm.getTitle());
		movie.setScore(movieForm.getScore());
		// 追加日時の登録
		movie.setCreatedAt(LocalDateTime.now());
		
		return this.movieRepositoy.save(movie);
	}

	// データの更新
	public Movie update(Integer id, MovieForm movieForm) {
		Movie movie = this.findById(id);
		movie.setTitle(movieForm.getTitle());
		movie.setScore(movieForm.getScore());
		// 更新日時も登録
		movie.setUpdateAt(LocalDateTime.now());

		return this.movieRepositoy.save(movie);
	}

	// データの削除(論理削除)
	public Movie delete(Integer id) {
		// idから該当のデータを取得
		Movie movie = this.findById(id);
		// deletedAtフィールドを上書き
		movie.setDeletedAt(LocalDateTime.now());
		
		return this.movieRepositoy.save(movie);
	}

	// deletedAtカラムがnullの情報を検索
	public List<Movie> findByDeletedAtIsNull() {
		return this.movieRepositoy.findByDeletedAtIsNull();
	}
}
