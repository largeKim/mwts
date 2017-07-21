package mwts.tomatc.member.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mwts.tomatc.member.service.LoginService;
import mwts.tomatc.member.service.LoginVO;

@Controller
public class UserController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/login.oc",method=RequestMethod.GET)
	public String getViewLogin(){
		return "login";
	}
	
	@RequestMapping(value="/login.oc",method=RequestMethod.POST)
	public ModelAndView checkUserLogin(
			@ModelAttribute("userVO")LoginVO userVO,
			HttpSession session){
		int result=loginService.loginDAO(userVO);
		ModelAndView mav=new ModelAndView();
		mav.setViewName("mwts01.member/msg/msg");
		if(result>0){
			session.setAttribute("userID", userVO.getUserid());
			mav.addObject("msg", "Welcome! "+userVO.getUserid()+"!");
			mav.addObject("locationOrder", "index.oc");
		}else{
			mav.addObject("msg", "Wrong Password!");
			mav.addObject("locationOrder", "login.oc");
		}
		return mav;
	}
	
	@RequestMapping(value="/index.oc",method=RequestMethod.GET)
	public String getViewIndex(){
		return "index";
	}
	
	@RequestMapping(value="/mindmap.oc",method=RequestMethod.GET)
	public String getViewMindmap(){
		return "mindmap/src/mindmap";
	}
}
