package com.Kash.KashDuv.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Kash.KashDuv.entity.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, String> {
    @Query("SELECT d FROM Despesa d WHERE d.usuario.username = :usuario AND "
            + "(coalesce(cast(:descricao as String), '') = '' OR lower(d.descricao) LIKE lower(concat('%', coalesce(cast(:descricao as String), ''), '%'))) AND "
            + "(coalesce(cast(:categoria as String), '') = '' OR lower(d.categoria) = lower(coalesce(cast(:categoria as String), ''))) AND "
            + "(:inicio IS NULL OR d.data >= :inicio) AND (:fim IS NULL OR d.data <= :fim)")
    Page<Despesa> buscar(@Param("usuario") String usuario, @Param("descricao") String descricao,
                         @Param("categoria") String categoria, @Param("inicio") LocalDate inicio,
                         @Param("fim") LocalDate fim, Pageable pageable);

    List<Despesa> findByUsuarioUsernameAndDataBetween(String usuario, LocalDate inicio, LocalDate fim);

    java.util.Optional<Despesa> findByIdAndUsuarioUsername(String id, String usuario);

    @Query("SELECT COALESCE(SUM(d.valor), 0) FROM Despesa d WHERE d.usuario.username = :usuario AND d.data BETWEEN :inicio AND :fim")
    BigDecimal totalNoPeriodo(@Param("usuario") String usuario, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT d.categoria AS categoria, COALESCE(SUM(d.valor), 0) AS total FROM Despesa d WHERE d.usuario.username = :usuario AND d.data BETWEEN :inicio AND :fim GROUP BY d.categoria")
    List<CategoriaTotal> totaisPorCategoria(@Param("usuario") String usuario, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
