package marst.util;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
/**
 * MD5的编码格式
 * @author taddy
 *
 */
public class MD5Encoder implements PasswordEncoder {

	public String encodePassword(String origPwd, Object salt)
			throws DataAccessException {
		return MD5.getMD5ofStr(origPwd);
	}

	public boolean isPasswordValid(String encPwd, String origPwd, Object salt)
			throws DataAccessException {
		return encPwd.equals(encodePassword(origPwd, salt));
	}

}
