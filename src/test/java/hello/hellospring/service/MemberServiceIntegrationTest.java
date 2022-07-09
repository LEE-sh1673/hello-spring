package hello.hellospring.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
	/*
		@SpringBootTest: 스프링 컨테이너와 테스트를 함께 실행한다.

		@Transactional
		DB 관련 테스트 실행 시 해당 어노테이션을 붙이면 테스트 수행 시 트랜잭션을 먼저
		수행을 하고 테스트 완료 후에 항상 rollback 한다. 그래서 DB에 데이터가 남지 않으므로
		다음 테스트에 영향을 주지 않는다.
	 */
	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName("회원가입 테스트")
	void shouldJoinCorrect() {
		// given
		Member member = new Member();
		member.setName("spring");

		// when
		Long saveId = memberService.join(member);

		// then
		Member findMember = memberService.findOne(saveId).get();
		assertThat(member.getName())
			.isEqualTo(findMember.getName());
	}

	@Test
	@DisplayName("중복 회원 예외 테스트")
	void shouldCatchDuplicatedMember() {
		// given
		Member member1 = new Member();
		member1.setName("spring");
		Member member2 = new Member();
		member2.setName("spring");

		// when
		memberService.join(member1);

		// then
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
			memberService.join(member2);
		});

		assertThat(e.getMessage())
			.isEqualTo("이미 존재하는 회원입니다.");
	}
}
