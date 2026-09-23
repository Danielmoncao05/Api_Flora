package com.senai.FloraSaaS.domain.repository.ambiente;

import com.senai.FloraSaaS.domain.entity.ambiente.Ambiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente, String> {
    // Metódo para buscar ambientes por id do idCliente
    List<Ambiente> findByCliente_IdUsuario(String idUsuario);

    // Métódo para buscar ambientes ativos
    List<Ambiente> findByAtivoTrue();

    // Métódo para buscar ambientes inativos
    List<Ambiente> findByAtivoFalse();
}
