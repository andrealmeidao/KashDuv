package com.Kash.KashDuv.repository;

import com.Kash.KashDuv.entity.Receita;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReceitaRepository extends JpaRepository<Receita, String> {
    @Query("SELECT r FROM Receita r WHERE r.usuario.username = :usuario AND "
            + "(:descricao IS NULL OR lower(r.descricao) LIKE lower(concat('%', :descricao, '%'))) AND "
            + "(:categoria IS NULL OR lower(r.categoria) = lower(:categoria)) AND "
            + "(:inicio IS NULL OR r.data >= :inicio) AND (:fim IS NULL OR r.data <= :fim)")
    Page<Receita> buscar(@Param("usuario") String usuario, @Param("descricao") String descricao,
                         @Param("categoria") String categoria, @Param("inicio") LocalDate inicio,
                         @Param("fim") LocalDate fim, Pageable pageable);

    List<Receita> findByUsuarioUsernameAndDataBetween(String usuario, LocalDate inicio, LocalDate fim);

    Optional<Receita> findByIdAndUsuarioUsername(String id, String usuario);
}
