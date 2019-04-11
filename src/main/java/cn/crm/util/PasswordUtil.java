package cn.crm.util;

import cn.crm.entity.SysAdminEntity;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;



/**
 * 加密信息
 */
public class PasswordUtil{

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private String algorithmName = "md5";
	private int hashIterations = 2;

	public void encryptPassword(SysAdminEntity adminEntity)
	{
		String salt = randomNumberGenerator.nextBytes().toHex();

		System.out.println("密码salt:为:"+salt);
        adminEntity.setAdmin_salt(salt);
		String newPassword = new SimpleHash(algorithmName, adminEntity.getAdmin_password(), ByteSource.Util.bytes(adminEntity.getAdmin_name()+salt), hashIterations).toHex();
        adminEntity.setAdmin_password(newPassword);
	}

	//测试
	public static void main(String[] args) {
		SysAdminEntity infoEntity = new SysAdminEntity();
		infoEntity.setAdmin_name("admin");
		infoEntity.setAdmin_password("saas123456");
		new PasswordUtil().encryptPassword(infoEntity);
		System.out.println(infoEntity);
	}


}
