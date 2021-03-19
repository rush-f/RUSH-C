package rush.rush;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("/test")
    public String hello(){

        return "안ㄴ명하센ㅇㅁㄹ욤ㄴㄹ";
    }
}
