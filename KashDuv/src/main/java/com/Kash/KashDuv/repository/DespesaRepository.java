package com.Kash.KashDuv.repository;

import com.Kash.KashDuv.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, String> {
    @Query("SELECT d FROM Despesa d WHERE d.usuario.username = :usuario AND "
            + "(:descricao IS NULL OR lower(d.descricao) LIKE lower(concat('%', :descricao, '%'))) AND "
            + "(:categoria IS NULL OR lower(d.categoria) = lower(:categoria)) AND "
            + "(:inicio IS NULL OR d.data >= :inicio) AND (:fim IS NULL OR d.data <= :fim)")
    Page<Despesa> buscar(@Param("usuario") String usuario, @Param("descricao") String descricao,
                         @Param("categoria") String categoria, @Param("inicio") LocalDate inicio,
                         @Param("fim") LocalDate fim, Pageable pageable);

    List<Despesa> findByUsuarioUsernameAndDataBetween(String usuario, LocalDate inicio, LocalDate fim);

    java.util.Optional<Despesa> findByIdAndUsuarioUsername(String id, String usuario);
}
