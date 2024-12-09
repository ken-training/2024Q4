package jp.ken.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class topController {
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String get(Model model) {

		return "top";
	}
}
