package com.anchor.security.util;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

import java.awt.*;

public class VerificationCodeUtil {
    /**
     * 生成验证码
     *
     * @param
     * @return 验证码
     */

    public static String[] generateCode(){
        // 设置字体
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.setFont(new Font("Times New Roman", Font.ITALIC, 34));
        System.out.println( captcha.getArithmeticString());
        System.out.println( captcha.textChar() );
        String[] verCode = new String[2];
        verCode[0]=captcha.getArithmeticString();
        verCode[1] =captcha.textChar().toString();
        return verCode;
    }
}
