package mwts.tomatc.member.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
	private int idx;
	private String userid;
	private String userpassword;
	private int name;
}
