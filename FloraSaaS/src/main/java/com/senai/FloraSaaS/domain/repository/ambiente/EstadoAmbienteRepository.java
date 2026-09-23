package com.senai.FloraSaaS.domain.repository.ambiente;

import com.senai.FloraSaaS.domain.entity.ambiente.EstadoAmbiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoAmbienteRepository extends JpaRepository<EstadoAmbiente, String> {
    // Métódo para encontrar o estado mais recente de um sensor específico
    //Optional<EstadoAmbiente> findTopByIdAmbienteOrderByDataHoraDesc(String idSensor);
}