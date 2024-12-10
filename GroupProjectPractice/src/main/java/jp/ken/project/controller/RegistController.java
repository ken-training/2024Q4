package jp.ken.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.ken.project.group.GroupOrder;
import jp.ken.project.model.CustomerModel;

@Controller
public class RegistController {


	@RequestMapping(value ="/regist",method=RequestMethod.GET)
	public String getRegist(Model model) {
		model.addAttribute("customerModel",new CustomerModel());
		return "regist";
	}

	@RequestMapping(value ="/regist",method=RequestMethod.POST)
	public String postRegist(@Validated(GroupOrder.class) @ModelAttribute CustomerModel customerMode,BindingResult result,Model model) {



		return "regist";
	}
}
