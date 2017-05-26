import com.kexie.acloud.dao.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by zojian on 2017/5/22.
 */

public class RedisTest extends BaseTest {
    @Autowired
    RedisTemplate<String,String > template;

    @Test
    public void test(){
        template.boundListOps("spring-test").leftPush("test");
    }
}
