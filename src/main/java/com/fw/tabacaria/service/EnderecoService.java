package com.fw.tabacaria.service;

import com.fw.tabacaria.domain.Endereco;
import com.fw.tabacaria.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Optional<Endereco> getById(Integer id) {
        return enderecoRepository.findById(id);
    }

    public Iterable<Endereco> getByIdUsuario(Integer idUsuario) {
        return enderecoRepository.findByIdUsuario(idUsuario);
    }

    public Endereco insert(Endereco endereco) {
        if ((endereco.getIdUsuario() == null) || (endereco.getIdUsuario() <= 0)) {
                throw new RuntimeException("Usuário não definido");
        }

        return enderecoRepository.save(endereco);
    }

    public Endereco update(Integer id, Endereco endereco) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Endereco> optional = getById(id);

        if (optional.isPresent()) {
            Endereco e = optional.get();
            e.setIdUsuario(endereco.getIdUsuario());
            e.setLogradouro(endereco.getLogradouro());
            e.setNumero(endereco.getNumero());
            e.setComplemento(endereco.getComplemento());
            e.setBairro(endereco.getBairro());
            e.setCidade(endereco.getCidade());
            e.setEstado(endereco.getEstado());
            e.setCep(endereco.getCep());
            e.setObservacao(endereco.getObservacao());

            enderecoRepository.save(e);

            return e;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if (getById(id).isPresent()) {
            enderecoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
