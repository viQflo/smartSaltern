package com.okjk.smartSaltern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okjk.smartSaltern.entity.TbEnv;

@Repository
public interface TbEnvRepository extends JpaRepository<TbEnv, Long> {
}
