package xzkbauth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import xzkbauth.util.SyUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XzkbAuthApplicationTests {

  @Autowired RedisTemplate redisTemplate;

  @Test
  public void contextLoads() {
    Map<String, String> map = new HashMap<>();
    map.put("大于", ">");
    map.put("小于", "<");

    redisTemplate.opsForValue().set("map123", map);
    Object o = redisTemplate.opsForValue().get("trigger_checkbox_value_map");
    System.out.println(o);
    if (o instanceof List) {
      System.out.println("list");
    }
    Object o2 = redisTemplate.opsForValue().get("token123");
    if (o2 instanceof String) {
      System.out.println("string");
    }
  }
}
