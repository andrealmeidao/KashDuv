package com.Kash.KashDuv.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Kash.KashDuv.entity.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, String> {
    @Query("SELECT r FROM Receita r WHERE r.usuario.username = :usuario AND "
            + "(coalesce(cast(:descricao as String), '') = '' OR lower(r.descricao) LIKE lower(concat('%', coalesce(cast(:descricao as String), ''), '%'))) AND "
            + "(coalesce(cast(:categoria as String), '') = '' OR lower(r.categoria) = lower(coalesce(cast(:categoria as String), ''))) AND "
            + "(:inicio IS NULL OR r.data >= :inicio) AND (:fim IS NULL OR r.data <= :fim)")
    Page<Receita> buscar(@Param("usuario") String usuario, @Param("descricao") String descricao,
                         @Param("categoria") String categoria, @Param("inicio") LocalDate inicio,
                         @Param("fim") LocalDate fim, Pageable pageable);

    List<Receita> findByUsuarioUsernameAndDataBetween(String usuario, LocalDate inicio, LocalDate fim);

    Optional<Receita> findByIdAndUsuarioUsername(String id, String usuario);

    @Query("SELECT COALESCE(SUM(r.valor), 0) FROM Receita r WHERE r.usuario.username = :usuario AND r.data BETWEEN :inicio AND :fim")
    BigDecimal totalNoPeriodo(@Param("usuario") String usuario, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT r.categoria AS categoria, COALESCE(SUM(r.valor), 0) AS total FROM Receita r WHERE r.usuario.username = :usuario AND r.data BETWEEN :inicio AND :fim GROUP BY r.categoria")
    List<CategoriaTotal> totaisPorCategoria(@Param("usuario") String usuario, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
