package jp.ken.project.model;

import java.io.Serializable;

public class SearchFormModel implements Serializable {
	private String category;		//選択したカテゴリ
	private String keyword;			//入力したキーワード

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
