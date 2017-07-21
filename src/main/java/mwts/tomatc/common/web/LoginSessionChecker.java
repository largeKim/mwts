package mwts.tomatc.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.servlet.ModelAndView;

public class LoginSessionChecker {
	public Object loginIDSessionChecker(ProceedingJoinPoint joinPoint) throws Throwable{
		String sigString=joinPoint.getSignature().toShortString();
		System.out.println(sigString+" 시작");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		ModelAndView mav=new ModelAndView();
		for(Object object : joinPoint.getArgs()){
			if(object instanceof HttpServletRequest){
				request = (HttpServletRequest)object;
			}
			if(object instanceof HttpServletResponse){
				response = (HttpServletResponse)object;
			}
		}
		try{
			HttpSession session = request.getSession();
			String loginID=(String)session.getAttribute("userID");
			System.out.println("userID Check: "+loginID);
			if(loginID==null||loginID.equals("")){
				mav.setViewName("mwts01.member/msg/msg");
				mav.addObject("locationOrder", "login.oc");
				mav.addObject("msg", "로그인을 해야 사용하실 수 있습니다.");
				return mav;
			}
		}catch(Exception e){
			mav.setViewName("mwts01.member/msg/msg");
			mav.addObject("locationOrder", "login.oc");
			mav.addObject("msg", "로그인을 해야 사용하실 수 있습니다.");
			return mav;
		}
		try{
			Object obj=joinPoint.proceed();
			return obj;
		}finally{
			System.out.println(sigString+" 종료");
		}
	}
}