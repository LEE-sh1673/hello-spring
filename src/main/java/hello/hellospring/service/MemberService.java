package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

public class MemberService {

	private final MemberRepository memberRepository = new MemoryMemberRepository();

	/**
	 * 회원 가입
	 * @param member 가입할 회원
	 * @return 가입한 회원의 ID 반환
	 */
	public long join(Member member) {
		// 같은 이름이 있는 중복된 회원 X
		validateDuplicatedMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicatedMember(Member member) {
		memberRepository.findByName(member.getName())
			.ifPresent(m -> {
				throw new IllegalStateException("이미 존재하는 회원입니다.");
			});
	}

	/**
	 * 전체 회원 조회
	 * @return {@code memberRepository}를 {@code List<Member>} 형태로 바꾸어 반환.
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
