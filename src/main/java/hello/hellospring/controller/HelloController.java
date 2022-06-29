package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	/*
		@GetMapping(route_path : str)
		-> Annotation for mapping HTTP GET requests onto specific handler methods.
		-> 즉 웹 애플리케이션에서 "~/hello"라는 형식으로 GET HTTP 요청이 온다면 해당 메서드를 호출한다.
	 */
	@GetMapping("hello")
	public String hello(Model model) {
		model.addAttribute("data", "LSH!");
        /*
            반환하는 문자열의 이름이 templates 에 있는 html 파일의 이름과 동일하다.
            즉, 이는 단순히 문자열을 반환하는 것이 아니라 해당 문자열에 해당하는 템플릿의 파일명을 찾아
            해당 파일을 렌더링 하는 것이다!

            컨트롤러에서 리턴 값으로 문자를 반환하면 "View Resolver" 가 화면을 찾아서 처리한다.
            이때 리턴 값이 바로 viewName 이다.

            -> 스프링 부트 템플릿 엔진 기본 viewName 매핑
            -> `resource:templates/' + (viewName) + `.html`
         */
		return "hello";
	}

	/*
		@RequestParam("query_attribute")
		흔히 웹 사이트 상에 URL 을 통해 무언가 query 를 하고 싶다면
		"~/PATH?QUERY_PARAM=VALUE"의 형식으로 찾게 된다. 이때 특정 경로 PATH 에 접속한 후
		서버에 query 하고자 하는 내용(QUERY_PARAM)과 함꼐 GET 요청을 보내고싶다면 해당 키워드를 쓸 수 있다.
	 */
	@GetMapping("hello-mvc")
	public String helloMVC(@RequestParam(value = "name", required = false) String name, Model model) {
		// Handling if name is null or empty.
		model.addAttribute("name", (name != null && !name.isEmpty()) ? name : "No-Name");

		// templates/hello-template.html 을 렌더링.
		return "hello-template";
	}

	/*
		@ResponseBody
		HTTP 의 Body 부분을 메서드를 통해 넘겨주겠다는 의미이다.
		예를 들어 아래 helloString() 메서드는 "~/hello-string" 형식으로 URL 을 요청했을 때
		HTTP Response 로 body 부분에 "hello ${name}"의 형식이 들어갈 것이다. 즉 이 경우 viewResolver + 템플릿 엔진의
		처리 없이 그대로 html 의 <body> 부분에도 문자열로 들어갈 것이다.
	 */
	@GetMapping("hello-string")
	@ResponseBody
	public String helloString(@RequestParam("name") String name) {
		return "hello " + name;
	}

	/*
		이렇게 HTTP 응답에 객체 형식으로 값을 반환하면
		JSON 형식으로 변환 후 클라이언트 쪽으로 전송된다.

		기본적으로 @ResponseBody 어노테이션이 붙은 경우 Json 형식으로
		반환하는 것이 기본이다.
	 */
	@GetMapping("hello-api")
	@ResponseBody
	public Hello helloApi(@RequestParam("name") String name) {
		final Hello hello = new Hello();
		hello.setName(name);
		return hello;
	}

	static class Hello {
		private String name;

		/*
			TODO: 스프링의 JSonConverter 규칙 관련 사례
			getter 메서드 이름의 관례는 get+필드명입니다.
			즉, 필드명이 name 이면 해당 메서드의 값을 가져오는 getter 메서드의 이름은 getName 이 되어야 합니다.
			이게 중요한 이유는 내부적으로 이런 관례를 이용하여 객체를 json 으로 매핑하기 때문입니다.
			따라서 json 에 key 값으로 'kkk' 가 나오게 하고 싶으시다면 getKkk() 로 메서드명을 작성하셔야 합니다.

			REF: https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%9E%85%EB%AC%B8-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/lecture/49578?volume=1.00&mm=close&tab=community&speed=1.25&q=545079
		 */
		public String getName() {
			return name;
		}

		public void setName(final String name) {
			this.name = name;
		}
	}
}
