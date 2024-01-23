package com.encore.basic.controller;

import com.encore.basic.domain.Hello;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

//모든 요청에 ResponseBody를 붙이고 싶다면, RestController 사용
@Controller
@RequestMapping("hello")
//클래스 차원에서 url경로를 지정하고 싶다면 @RequestMapping을 클래스 위에 선언하면서 경로지정
public class HelloController {

    //    @responseBody가 없고,return 타입이 String이면 templates밑에 html 파일 리턴
//    data만을 return할 때는 @ResponseBody를 붙인다.
    @GetMapping("string")
//    @RequestMapping(value = "string", method = RequestMethod.GET)
    @ResponseBody
    public String helloString() {
        return "hello_string";
    }

    @GetMapping("json")
    @ResponseBody
    public Hello helloJson() {
        Hello hello = new Hello();
        hello.setName("hong");
        hello.setPassword("1234");
        hello.setEmail("gildong@naver.coom");
        System.out.println(hello);
        return hello;
    }

    @GetMapping("screen")
    public String helloScreen() {
        return "screen";
    }

    @GetMapping("screen-model-param")
//    ?name==hongildong의 방식으로 호출(파라미터 호출 방식)
    public String helloScreenModelParam(@RequestParam(value = "name") String inputName, Model model) {
//        화면에 data를 넘기고 싶을때는 model 객체 사용
//        model에 key:values 형싱으로 전달
        model.addAttribute("myData", inputName);
        return "screen";
    }

    //    pathvaiable방식은 url을 통해 자원의 구조를 명확하게 표현할 수 있어,
//    좀 더 RestFul API디자인에 적합
    @GetMapping("screen-model-path/{id}")
    public String helloScreenModelPath(@PathVariable int id, Model model) {
        model.addAttribute("myData", id);
        return "screen";
    }

    //    Form 태그로 x-www 데이터 처리
    @GetMapping("form-screen")
    public String FormScreen() {
        return "hello-form-screen";
    }

    @PostMapping("/form-post-handle")
    @ResponseBody
//    form태그를 통한 body의 데이터 형태가 key1=vaule1&key2=value2
    public String FormPostHandle(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "email") String email,
                                 @RequestParam(value = "password") String password) {
        System.out.println("이름 : " + name);
        System.out.println("email : " + email);
        System.out.println("password : " + password);
        return "정상처리";
    }

    @PostMapping("/form-post-handle2")
    @ResponseBody
//    Spring에서 Hello클래스의 인스턴스를 자동 매핑하여 생성
//    form-data형식 즉, x-www-url 인코딩 형식의 경우 사용
//    이를 데이터 바인딩이라 부른다. (Hello클래스에 setter필수)
    public String FormPostHandle2(Hello hello) {
        System.out.println(hello);
        return "정상처리";
    }

    //    json데이터 처리
    @GetMapping("json-screen")
    public String jsonScreen() {
        return "hello-json-screen";
    }

    @PostMapping("/json-post-handle1")
    @ResponseBody
//    @RequestBody는 post 요청이 들어왔을 때 body에서 data를 꺼내기 위해 사용
    public String jsonPostHandle1(@RequestBody Map<String, String> body) {
        System.out.println("이름 : " + body.get("name"));
        System.out.println("email : " + body.get("email"));
        System.out.println("password : " + body.get("password"));
        Hello hello = new Hello();
        hello.setName(body.get("name"));
        hello.setEmail(body.get("email"));
        hello.setPassword(body.get("password"));
        return "ok";
    }

    @PostMapping("/json-post-handle2")
    @ResponseBody
    public String jsonPostHandle2(@RequestBody JsonNode body) {
        Hello hello = new Hello();
        hello.setName(body.get("name").asText());
        hello.setEmail(body.get("email").asText());
        hello.setPassword(body.get("password").asText());
        return "ok";
    }

    @PostMapping("/json-post-handle3")
    @ResponseBody
    public String jsonPostHandle3(@RequestBody Hello hello) {
        System.out.println(hello);
        return "ok";
    }

    @PostMapping("httpservlet")
    @ResponseBody
    public String httpServletTest(HttpServletRequest req){
//        HttpServletRequest객체에서 header정보 추출
        System.out.println(req.getContentType());
        System.out.println(req.getMethod());
//        session : 로그인 정보에서 필요한 정보값을 추출할때 많이 사용
        System.out.println(req.getSession());
        System.out.println(req.getHeader("Accept"));


//        HttpServletRequest객체에서 body 정보 추출
        System.out.println(req.getParameter("test1"));
        System.out.println(req.getParameter("test2"));
//        req.getReader()를 통해 BufferReader로 받아 직접 파싱
        return "OK";
    }

    @GetMapping("/hello-servlet-jsp-get")
    public String helloServletJspGet(Model model){
        model.addAttribute("myData", "jsp test data");
        return "hello-jsp";
    }

    public void helloBuilderTest(){
        Hello hello = Hello.builder()
                .name("hong").email("hong@naver.com")
                .build();
    }


}
