package com.kedang.fenxiao.util.captcha;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Test {
	public static void main(String[] args) throws FileNotFoundException {
		Captcha captcha = new SpecCaptcha(150, 40, 5);// png格式验证码
		captcha.out(new FileOutputStream("/Users/gegongxian/work/captcha/1.png"));
		captcha = new GifCaptcha(150, 40, 5);// gif格式动画验证码
		captcha.out(new FileOutputStream("/Users/gegongxian/work/captcha/1.gif"));
	}
}
