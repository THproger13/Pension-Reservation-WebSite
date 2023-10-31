package com.example.reservation.repository;

import com.example.reservation.entity.MemberEntity;
import com.example.reservation.entity.ReserveEntity;
import com.example.reservation.entity.ReserveWaitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReserveWaitRepository extends JpaRepository<ReserveWaitEntity, Long> {
    Optional<ReserveWaitEntity> findByReserveEntityAndMemberEntity(ReserveEntity reserveEntity, MemberEntity memberEntity);
}
