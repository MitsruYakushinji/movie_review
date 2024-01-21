package com.example.form;

//リクエストの送受信
public class MovieForm {
	private String title;
	private Integer score;

	// titleのアクセサー
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// scoreのアクセサー
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
