package com.fw.tabacaria.service;

import com.fw.tabacaria.domain.Mercadoria;
import com.fw.tabacaria.domain.Usuario;
import com.fw.tabacaria.domain.UsuarioMercadoria;
import com.fw.tabacaria.repository.UsuarioMercadoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class UsuarioMercadoriaService {
    @Autowired
    private UsuarioMercadoriaRepository usuarioMercadoriaRepository;

    public Iterable<UsuarioMercadoria> getUsuarioMercadoria() {
        return usuarioMercadoriaRepository.findAll();
    }

    public Optional<UsuarioMercadoria> getById(Integer id){
        return usuarioMercadoriaRepository.findById(id);
    }

    public Iterable<UsuarioMercadoria> getByUsuario(Usuario usuario) {
        return usuarioMercadoriaRepository.findByUsuario(usuario);
    }

    public Iterable<UsuarioMercadoria> getByMercadoria(Mercadoria mercadoria) {
        return usuarioMercadoriaRepository.findByMercadoria(mercadoria);
    }

    public Optional<UsuarioMercadoria> getByUsuarioMercadoria(Usuario usuario, Mercadoria mercadoria){
        return usuarioMercadoriaRepository.findByUsuarioMercadoria(usuario.getId(), mercadoria.getId());
    }


    public UsuarioMercadoria insert(UsuarioMercadoria usuarioMercadoria) {
        if ((usuarioMercadoria.getId() == null) || (usuarioMercadoria.getId() <= 0)) {
            Optional<UsuarioMercadoria> op1 = getByUsuarioMercadoria(usuarioMercadoria.getUsuario(), usuarioMercadoria.getMercadoria());
            if (op1.isPresent()){
                throw new RuntimeException("Mercadoria já existe para esse Usuário");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return usuarioMercadoriaRepository.save(usuarioMercadoria);
    }

    public UsuarioMercadoria update(Integer id, UsuarioMercadoria usuarioMercadoria) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<UsuarioMercadoria> optional = getById(id);

        if (!optional.get().getMercadoria().equals(usuarioMercadoria.getMercadoria())) {
            Optional<UsuarioMercadoria> op1 = getByUsuarioMercadoria(usuarioMercadoria.getUsuario(), usuarioMercadoria.getMercadoria());
            if (op1.isPresent()) {
                throw new RuntimeException("Não foi possível atualizar o registro, Mercadoria já existe para esse Usuário");
            }
        }

        if (optional.isPresent()) {
            UsuarioMercadoria u = optional.get();
            u.setPreco(usuarioMercadoria.getPreco());
            u.setQuantidade(usuarioMercadoria.getQuantidade());

            usuarioMercadoriaRepository.save(u);

            return u;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            usuarioMercadoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
