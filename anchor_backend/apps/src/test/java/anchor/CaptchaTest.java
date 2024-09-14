package anchor;

import com.anchor.AnchorApplication;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest(classes = AnchorApplication.class)
public class CaptchaTest {
    @Test
    public void t2() throws IOException {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);

        String text = captcha.text();
        System.out.println();
//        specCaptcha.out(Files.newOutputStream(new File("d:/ff.jpeg").toPath()));
    }

}
