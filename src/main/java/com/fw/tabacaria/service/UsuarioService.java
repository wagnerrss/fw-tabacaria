package com.fw.tabacaria.service;

import com.fw.tabacaria.domain.Usuario;
import com.fw.tabacaria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Iterable<Usuario> getUsers() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> getByDocumento(String documento) {
        return usuarioRepository.findByDocumento(documento);
    }

    public Optional<Usuario> getByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario insert(Usuario user) {
        if ((user.getId() == null) || (user.getId() <= 0)) {
            Optional<Usuario> op1 = getByDocumento(user.getDocumento());
            Optional<Usuario> op2 = getByEmail(user.getEmail());
            if ((op1.isPresent()) || (op2.isPresent())) {
                throw new RuntimeException("Usuário já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return usuarioRepository.save(user);
    }

    public Usuario update(Integer id, Usuario usuario) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Usuario> optional = getById(id);

        if (!optional.get().getDocumento().equals(usuario.getDocumento())) {
            Optional<Usuario> optionalDocumento = getByDocumento(usuario.getDocumento());
            if (optionalDocumento.isPresent()) {
                throw new RuntimeException("Não foi possível atualizar o registro, Documento já existe");
            }
        }

        if (!optional.get().getEmail().equals(usuario.getEmail())) {
            Optional<Usuario> optionalEmail = getByEmail(usuario.getEmail());
            if (optionalEmail.isPresent()) {
                throw new RuntimeException("Não foi possível atualizar o registro, Email já existe");
            }
        }

        if (optional.isPresent()) {
            Usuario u = optional.get();
            u.setFoto(usuario.getFoto());
            u.setNome(usuario.getNome());
            u.setDocumento(usuario.getDocumento());
            u.setEmail(usuario.getEmail());
            u.setTelefone(usuario.getTelefone());
            u.setSenha(usuario.getSenha());
            u.setTipo(usuario.getTipo());
            u.setToken(usuario.getToken());

            usuarioRepository.save(u);

            return u;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Usuario findUser(Map login) {
        Usuario u = usuarioRepository.findByEmailDocumentoSenha(login.get("email").toString(), login.get("documento").toString(), login.get("senha").toString());

        return u;
    }

}
