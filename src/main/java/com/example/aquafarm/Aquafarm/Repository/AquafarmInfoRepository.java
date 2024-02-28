package com.example.aquafarm.Aquafarm.Repository;

import com.example.aquafarm.Aquafarm.Domain.AquafarmInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AquafarmInfoRepository extends JpaRepository<AquafarmInfo, Integer> {
    List<AquafarmInfo> findByAddress(String address);
    List<AquafarmInfo> findByUserId(int userId);
    List<AquafarmInfo> findByAddressContaining(String keyword);
    List<AquafarmInfo> findByCustomCriteria(String criteria);
    Optional<AquafarmInfo> findById(int aquafarmId);
    Optional<AquafarmInfo> findFirstByOrderByAquafarmId();

}
