package anchor;

import com.anchor.AnchorApplication;
import com.anchor.sys.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = AnchorApplication.class)
public class MenuTest
{
    @Autowired
    MenuMapper menuMapper;
    @Test
    public void contextLoads(){
        List<String> list = menuMapper.selectPermsByUserId(3l);
        System.out.println(list);
    }
}
