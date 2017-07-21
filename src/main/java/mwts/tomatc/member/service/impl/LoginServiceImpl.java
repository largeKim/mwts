package mwts.tomatc.member.service.impl;

import org.mybatis.spring.SqlSessionTemplate;

import lombok.AllArgsConstructor;
import mwts.tomatc.member.service.LoginService;
import mwts.tomatc.member.service.LoginVO;

@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

	private SqlSessionTemplate sqlMap;
	private final int SOMETHING_ERROR=-3;
	private final int NOT_CORRECT_PASSWORD=-2;
	private final int MAKE_USER_ID=5;
	private final int LOGIN_SUCCESS=1;
	
	@Override
	public int loginDAO(LoginVO userVO) {
		int result=sqlMap.selectOne("checkUserID", userVO);
		if(result>0){
			String password=sqlMap.selectOne("fetchUserPassword", userVO);
			if(!(password.equals(userVO.getUserpassword()))){
				return NOT_CORRECT_PASSWORD;
			}
		}else{
			result=sqlMap.insert("makeUserID", userVO);
			return result>0?MAKE_USER_ID:SOMETHING_ERROR;
		}
		return LOGIN_SUCCESS;
	}
}
