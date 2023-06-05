package com.example.aquafarm.Aquafarm.Repository;

import com.example.aquafarm.Aquafarm.Domain.AquafarmInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AquafarmInfoRepository extends JpaRepository<AquafarmInfo, Integer> {
    List<AquafarmInfo> findByAddress(String address);
    // address로 AquafarmInfo를 조회하는 메소드

    List<AquafarmInfo> findByUserId(int userId);
    // userId로 AquafarmInfo를 조회하는 메소드

    List<AquafarmInfo> findByAddressContaining(String keyword);

    List<AquafarmInfo> findByCustomCriteria(String criteria);

    Optional<AquafarmInfo> findById(int aquafarmId);

    //AquafarmInfo findById(int aquafarmId);
    // 특정 키워드를 포함하는 address를 가진 AquafarmInfo를 조회하는 메소드

    Optional<AquafarmInfo> findFirstByOrderByAquafarmId();

}
