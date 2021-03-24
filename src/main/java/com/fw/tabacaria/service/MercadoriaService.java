package com.fw.tabacaria.service;

import com.fw.tabacaria.domain.Mercadoria;
import com.fw.tabacaria.repository.MercadoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class MercadoriaService {
    @Autowired
    private MercadoriaRepository mercadoriaRepository;

    public Iterable<Mercadoria> getMercadorias() {
        return mercadoriaRepository.findAll();
    }

    public Optional<Mercadoria> getById(Integer id) {
        return mercadoriaRepository.findById(id);
    }

    public Iterable<Mercadoria> getByUsuario(Integer idUsuario){
        return mercadoriaRepository.findByIdUsuario(idUsuario);
    }

    public Iterable<Mercadoria> getByCategoria(Integer idCategoria){
        return mercadoriaRepository.findByIdCategoria(idCategoria);
    }

    public Optional<Mercadoria> getByNome(String nome) {
        return mercadoriaRepository.findByNome(nome);
    }

    public Optional<Mercadoria> getByDescricao(String descricao) {
        return mercadoriaRepository.findByDescricao(descricao);
    }

    public Iterable<Mercadoria> getByNomeDescricao(String nome, String descricao){
        return mercadoriaRepository.findByNomeDescricao(nome, descricao);
    }

    public Optional<Mercadoria> getByIdUsuarioNome(Integer idUsuario, String nome) {
        return mercadoriaRepository.findByIdUsuarioNome(idUsuario, nome);
    }

    public Mercadoria insert(Mercadoria mercadoria) {
        if ((mercadoria.getId() == null) || (mercadoria.getId() <= 0)) {
            Optional<Mercadoria> op1 = getByIdUsuarioNome(mercadoria.getIdUsuario(), mercadoria.getNome());
            if (op1.isPresent()){
                throw new RuntimeException("Mercadoria já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return mercadoriaRepository.save(mercadoria);
    }

    public Mercadoria update(Integer id, Mercadoria mercadoria) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Mercadoria> optional = getById(id);

        if (!optional.get().getNome().equals(mercadoria.getNome())) {
            Optional<Mercadoria> optionalNome = getByIdUsuarioNome(optional.get().getIdUsuario(), mercadoria.getNome());
            if (optionalNome.isPresent()) {
                throw new RuntimeException("Não foi possível atualizar o registro, Mercadoria já existe");
            }
        }

        if (optional.isPresent()) {
            Mercadoria m = optional.get();
            m.setIdCategoria(mercadoria.getIdCategoria());
            m.setFoto(mercadoria.getFoto());
            m.setNome(mercadoria.getNome());
            m.setDescricao(mercadoria.getDescricao());
            m.setPreco(mercadoria.getPreco());
            m.setTipo(mercadoria.getTipo());
            m.setQuantidade(mercadoria.getQuantidade());

            mercadoriaRepository.save(m);

            return m;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            mercadoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
