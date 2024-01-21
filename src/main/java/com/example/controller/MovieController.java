package com.example.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Movie;
import com.example.form.MovieForm;
import com.example.service.MovieService;

@Controller
@RequestMapping("/movie")
public class MovieController {
	private final MovieService movieService;

	@Autowired
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	// 映画評価一覧表示
	@GetMapping("/list")
	public String list(Model model) {
		// deletedAtカラムがnullのデータのみを検索
		List<Movie> movies = this.movieService.findByDeletedAtIsNull();

		// ID順でソート
		movies.sort(Comparator.comparing(Movie::getId));
		model.addAttribute("movies", movies);
		return "movie/list";
	}
	
	// 映画情報追加ページ表示用
	@GetMapping("/addMovie")
	public String addMoviePage(@ModelAttribute("movieForm") MovieForm movieForm) {
		
		return "movie/addMoviePage";
	}
	
	// 映画情報追加の実行
	@PostMapping("/addMovie")
	public String addMovie(MovieForm movieForm) {
		this.movieService.save(movieForm);
		
		return "redirect:/movie/list";
	}

	// 映画情報編集ページ表示用
	@GetMapping("edit/{id}")
	public String editPage(@PathVariable("id") Integer id, Model model,
			@ModelAttribute("movieForm") MovieForm movieForm) {
		Movie movie = this.movieService.findById(id);
		movieForm.setTitle(movie.getTitle());
		movieForm.setScore(movie.getScore());

		model.addAttribute("id", id);
		return "movie/editPage";
	}

	// 映画情報編集実行
	@PostMapping("edit/{id}")
	public String edit(@PathVariable("id") Integer id, @ModelAttribute("movieForm") MovieForm movieForm, RedirectAttributes ra) {
		this.movieService.update(id, movieForm);
		ra.addFlashAttribute("success_message", "更新が完了しました。");
		return "redirect:/movie/list";
	}

	// 映画情報削除(論理削除)
	@PostMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
		this.movieService.delete(id);
		ra.addFlashAttribute("success_message", "削除が完了しました。");
		return "redirect:/movie/list";
	}

}
