package hello.hellospring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import hello.hellospring.service.MemberService;

/**
 {@code @Controller}는 무슨 의미일까?

 -> 처음 클래스의 어노테이션으로 이를 붙이면 어떤 일이 발생할까? 처음 스프링은 컨테이너라는 커다란 통을 생성하는데,
 이때 해당 노테이션이 붙은 클래스가 존재하면 맴버 컨트롤러 객체를 생성하여 컨테이너 안에 넣어두게 된다. 즉 스프링이 해당 객체를 관리하게 된다.
 */
@Controller
public class MemberController {
	private final MemberService memberService;

	/**
	 * 생성자에 Autowired 키워드가 붙는다면 스프링이 연관된 객체를
	 * 스프링 컨테이너 안에서 찾아 연결시켜준다.
	 * (이렇게 객체 의존관계를 외부에서 넣어주는 것을 D.I라고 한다. -> 여기서는 스프링에 의해서 주입됨.)
	 *
	 *
	 * Parameter 0 of constructor in hello.hellospring.controller.MemberController
	 * required "a bean of type" 'hello.hellospring.service.MemberService' that could not be found.
	 */
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
}
