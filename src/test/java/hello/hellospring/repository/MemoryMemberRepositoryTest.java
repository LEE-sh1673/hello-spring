package hello.hellospring.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;

class MemoryMemberRepositoryTest {

	MemoryMemberRepository repository = new MemoryMemberRepository();

	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}

	@Test

	public void shouldSaveMemberCorrect() {
		// Give
		Member member = new Member();
		member.setName("Spring");
		// When
		repository.save(member);
		Member result = repository.findById(member.getId()).get();
		// Then
		Assertions.assertThat(member).isEqualTo(result);
	}

	@Test
	public void shouldFindByNameCorrect() {
		// Give
		Member member1 = new Member();
		member1.setName("spring1");
		Member member2 = new Member();
		member2.setName("spring2");

		// When
		repository.save(member1);
		repository.save(member2);
		Member result = repository.findByName("spring1").get();

		// Then
		Assertions.assertThat(result).isEqualTo(member1);
	}

	@Test
	public void shouldFindAllCorrect() {
		Member member3 = new Member();
		member3.setName("spring1");
		repository.save(member3);

		Member member4 = new Member();
		member3.setName("spring2");
		repository.save(member4);

		List<Member> members = repository.findAll();
		Assertions.assertThat(members.size()).isEqualTo(2);
	}
}


