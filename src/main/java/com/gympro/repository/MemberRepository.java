package com.gympro.repository;

import com.gympro.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR m.phone LIKE CONCAT('%', :keyword, '%')")
    List<Member> searchByNameOrPhone(@Param("keyword") String keyword);

    List<Member> findTop5ByOrderByJoinDateDesc();
}
