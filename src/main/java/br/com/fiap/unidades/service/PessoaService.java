package br.com.fiap.unidades.service;

import br.com.fiap.unidades.dto.request.PessoaRequest;
import br.com.fiap.unidades.dto.response.PessoaResponse;
import br.com.fiap.unidades.entity.Pessoa;
import br.com.fiap.unidades.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PessoaService implements ServiceDTO<Pessoa, PessoaRequest, PessoaResponse>{

    @Autowired
    private PessoaRepository pessoaRepo;

    @Override
    public Pessoa toEntity(PessoaRequest r) {
        if (Objects.isNull(r)) return null;
        return Pessoa.builder()
                .nome(r.nome())
                .sobrenome(r.sobrenome())
                .email(r.email())
                .tipo(r.tipo())
                .nascimento(r.nascimento())
                .build();
    }

    @Override
    public PessoaResponse toResponse(Pessoa e) {
        return PessoaResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .sobrenome(e.getSobrenome())
                .email(e.getEmail())
                .tipo(e.getTipo())
                .nascimento(e.getNascimento())
                .build();
    }

    @Override
    public List<Pessoa> findAll() {
        return pessoaRepo.findAll();
    }

    @Override
    public List<Pessoa> findAll(Example<Pessoa> example) {
        return pessoaRepo.findAll(example);
    }

    @Override
    public Pessoa findById(Long id) {
        return pessoaRepo.findById(id).orElse(null);
    }

    @Override
    public Pessoa save(Pessoa e) {
        return pessoaRepo.save(e);
    }
}
