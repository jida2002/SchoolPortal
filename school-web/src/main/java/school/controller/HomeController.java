package school.controller;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import school.model.User;
import school.service.HomeService;
import school.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private HomeService homeService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "home")
	public String index(Model model, Principal principal, HttpServletRequest request) {
		model.addAttribute("newsList", homeService.findAllNews());
		HttpSession session = request.getSession(false);
		if (principal != null && session.getAttribute("user_name") == null){
			User user = userService.loadUser(Integer.parseInt(principal.getName()));
			session.setAttribute("user_name", user.getFirstName() + " " +user.getLastName());
		}
		return "home";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String logIn(Model model) {
		model.addAttribute("newsList", homeService.findAllNews());
		model.addAttribute("visible_login", "visible_error");
		return "signinfailure";
	}

	@RequestMapping(value = "signinfailure", method = RequestMethod.GET)
	public String signinFailure(Model model) {
		model.addAttribute("newsList", homeService.findAllNews());
		model.addAttribute("visible_signinfailure", "visible_error");
		return "signinfailure";
	}
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registrationConfirm(Model model,
			@ModelAttribute(value = "u") long userId,
			@ModelAttribute(value = "c") int code) {
		User user = homeService.confirmUser(userId, code);
		model.addAttribute("newsList", homeService.findAllNews());
		model.addAttribute("login_action", "../");
		if(user != null)
		model.addAttribute("user_email", user.getEmail());
		return "signinfailure";
	}
	@RequestMapping(value = "/forgotemail/check", method = RequestMethod.GET)
	public @ResponseBody boolean forgotemailCheck(
			@RequestParam(value = "email") String email) {
		return userService.isEmailAviable(email);
	}
	@RequestMapping(value = "/forgotemail", method = RequestMethod.GET)
	public String forgotemail(Model model,
			@ModelAttribute(value = "u") long userId,
			@ModelAttribute(value = "c") int code) {
		User user = homeService.confirmUser(userId, code);
		model.addAttribute("newsList", homeService.findAllNews());
		model.addAttribute("login_action", "../");
		if(user != null)
		model.addAttribute("user_email", user.getEmail());
		return "signinfailure";
	}

	@RequestMapping(value = "/registration/check", method = RequestMethod.POST)
	public @ResponseBody boolean registration(HttpServletRequest request,
			@RequestParam(value = "firstname") String firstName,
			@RequestParam(value = "lastname") String lastName,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "sex") Byte sex,
			@RequestParam(value = "role") Integer role) {
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setRegistration(new Date());
		user.setSex(sex);
		user.setConfirmed(User.ConfirmType.UCONFIRMED);
		return homeService.registrateUser(user, role, request.getContextPath());
	}

	@RequestMapping(value = "/email/check", method = RequestMethod.GET)
	public @ResponseBody boolean check(
			@RequestParam(value = "email") String email) {
		return userService.isEmailAviable(email);
	}
}
