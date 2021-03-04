import com.ylg.YlgApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YlgApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test(){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("name","key",1,TimeUnit.MINUTES);
        operations.getOperations().delete("name");
        System.out.println(operations.get("name"));
    }

    @Test
    public void test1(){
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        int temp = cal.get(Calendar.DATE);
        System.out.println(temp);

    }
}
