package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

@Transactional
public class MemberService {
    /*
    	import org.springframework.transaction.annotation.Transactional;
    	-> 테스트 코드와 비지니스 로직 간의 차이점.

    	@Transactional은 기본적으로 해당 메서드가 실행될 때 트랜잭션을 시작하고
    	해당 메서드가 끝날 때 커밋을 해서 데이터베이스에 전달된 내용을 확정합니다.

		그런데 테스트 코드에서 @Transactional을 사용하면 커밋을 하는게 아니라
		데이터베이스에 롤백을 해서 데이터베이스에 전달된 데이터를 모두 삭제됩니다.

		[출처: https://www.inflearn.com/questions/112512]
     */
	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

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
