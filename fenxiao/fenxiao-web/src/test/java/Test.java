import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

public class Test
{
	public static void main(String[] args) {
		List<String> list = entryptPassword("123456","012765672ef38d30");
		System.out.println(list.get(0).toString()+"----1");
//		AccountService ac = new AccountService();
//		List<String> list = ac.entryptPassword("654321","37849f7f45b472c2");
//		System.out.println(list.get(0).toString()+"----1");
//		List<String> list2 = ac.entryptPassword("654321","37849f7f45b472c2");
//		System.out.println(list2.get(0).toString()+"----2");\

	}
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public static List<String> entryptPassword(String password, String salt)
	{
		List<String> list = new ArrayList<String>();
		byte[] byteSalt = null;
		if (StringUtils.isBlank(salt))
		{
			byteSalt = Digests.generateSalt(8);
			salt = Encodes.encodeHex(byteSalt);
		}
		else
		{
			byteSalt = Encodes.decodeHex(salt);
		}
		byte[] hashPassword = Digests.sha1(password.getBytes(), byteSalt, 1024);
		list.add(Encodes.encodeHex(hashPassword));
		list.add(salt);
		return list;
	}
	
}
