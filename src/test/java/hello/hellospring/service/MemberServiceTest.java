package hello.hellospring.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;

class MemberServiceTest {

	private MemberService memberService;

	private MemoryMemberRepository memberRepository;

	@BeforeEach
	public void beforeEach() {
		memberRepository = new MemoryMemberRepository();
		memberService = new MemberService(memberRepository);
	}

	@AfterEach
	public void afterEach() {
		memberRepository.clearStore();
	}

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

		// try {
		// 	memberService.join(member2);
		// 	Assertions.fail("예외가 발생해야 합니다!");
		// } catch (IllegalStateException e) {
		// 	Assertions.assertThat(e.getMessage())
		// 		.isEqualTo("이미 존재하는 회원입니다.");
		// }

		// then
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
			memberService.join(member2);
		});

		assertThat(e.getMessage())
			.isEqualTo("이미 존재하는 회원입니다.");
	}

	@Test
	@DisplayName("전체 회원 조회 테스트")
	void shouldFindMembersCorrect() {

	}

	@Test
	@DisplayName("단일 회원 조회 테스트")
	void ShouldFindOneMemberCorrect() {

	}
}
