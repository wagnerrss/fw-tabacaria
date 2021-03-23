package com.fw.tabacaria.service;

import com.fw.tabacaria.domain.Categoria;
import com.fw.tabacaria.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Iterable<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> getById(Integer id) {
        return categoriaRepository.findById(id);
    }

    public Optional<Categoria> getByDescricao(String descricao) {
        return categoriaRepository.findByDescricao(descricao);
    }

    public Categoria insert(Categoria categoria) {
        if ((categoria.getId() == null) || (categoria.getId() <= 0)) {
            Optional<Categoria> op1 = getByDescricao(categoria.getDescricao());
            if (op1.isPresent()){
                throw new RuntimeException("Categoria já existe");
            }
        } else {
            throw new RuntimeException("Não foi possível inserir o registro");
        }

        return categoriaRepository.save(categoria);
    }

    public Categoria update(Integer id, Categoria categoria) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Categoria> optional = getById(id);

        if (!optional.get().getDescricao().equals(categoria.getDescricao())) {
            Optional<Categoria> optionalDescricao = getByDescricao(categoria.getDescricao());
            if (optionalDescricao.isPresent()) {
                throw new RuntimeException("Não foi possível atualizar o registro, Categoria já existe");
            }
        }

        if (optional.isPresent()) {
            Categoria c= optional.get();
            c.setDescricao(categoria.getDescricao());

            categoriaRepository.save(c);

            return c;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
