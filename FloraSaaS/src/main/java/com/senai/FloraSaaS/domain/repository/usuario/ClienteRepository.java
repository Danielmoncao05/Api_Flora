package com.senai.FloraSaaS.domain.repository.usuario;

import com.senai.FloraSaaS.domain.entity.usuario.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    // Métódo para buscar clientes ativos
    List<Cliente>findByAtivoTrue();

    // Métódo para buscar clientes inativos
    List<Cliente>findByAtivoFalse();
}