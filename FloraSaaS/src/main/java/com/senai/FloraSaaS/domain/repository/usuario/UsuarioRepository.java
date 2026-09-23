package com.senai.FloraSaaS.domain.repository.usuario;

import com.senai.FloraSaaS.domain.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    // Métódo para encontrar um usuário pelo email
    Optional<Usuario> findByEmail(String email);
}