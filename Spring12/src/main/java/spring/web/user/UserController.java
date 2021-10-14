package spring.web.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.domain.User;
import spring.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	public UserController() {
		// TODO Auto-generated constructor stub
		System.out.println("==>UserController default Constructor call.....");
	}
	
	@RequestMapping("/logon")
	public String logon(Model model) {
		System.out.println("\n:: ==> logon() start....");
		
	String message = "[logon()] 아이디,패스워드 3자이상 입력하세요.";
	
		//System.out.println("\n:: ==> logon() start....");
		model.addAttribute("message",message);
		
		System.out.println("\n:: ==> logon() end....");
		return "/user/logon.jsp";
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		
		System.out.println("\n:: ==> home() start....");
		String message = "[home() ]WELCOME";
		
		model.addAttribute("message",message);
		
		System.out.println("\n:: ==> home() end....");
		
		return "/user/home.jsp";
	}
	@RequestMapping(value="/logonAction", method=RequestMethod.POST)
	public String logonAction(@ModelAttribute("user")User user,
								Model model, HttpSession session)throws Exception {
		
		System.out.println("\n:: ==> logonAction() start....");
		
		//String message = "[home() ]WELCOME";
		String viewName = "/user/logon.jsp";
		User returnUser = userService.getUser(user.getUserId());
		if(returnUser.getPassword().equals(user.getPassword())) {
			returnUser.setActive(true);
			user = returnUser;
		}
		if(user.isActive()) {
			viewName = "/user/home.jsp";
			session.setAttribute("sessionUser", user);
		}
		System.out.println("[action :"+viewName+"]");
		
		String message = null;
		if(viewName.equals("/user/home.jsp")) {
			message = "[LohonAction WECLCOME";
		}else {
			message = "[LohonAction() 아이디 패스워드 3자이상 ";
		}
		
		model.addAttribute("message", message);
	
		
		System.out.println("\n:: ==> logonAction() end....");
		
		return viewName;
	}
	
	
	@RequestMapping("/logout")
	public String logout(Model model,HttpSession session) {
		
		System.out.println("\n:: ==> logout() start....");
		
		session.removeAttribute("sessionUser");
		
		String message = "[Logout()] 아이디,패스워드 3자이상 입력하세요.";
			model.addAttribute("message", message);

		System.out.println("\n:: ==> logout() end....");
		
		return "/user/logon.jsp";
	}

}
