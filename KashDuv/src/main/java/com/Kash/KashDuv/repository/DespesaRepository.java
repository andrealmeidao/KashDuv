package com.Kash.KashDuv.repository;

import com.Kash.KashDuv.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, String> {
}