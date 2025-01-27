package io.personal.stock.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.personal.stock.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Object> {
    public Optional<Member> findByIdAndPasswd(Long id, String passwd);

    public Optional<Member> findByEmail(String email);
}
