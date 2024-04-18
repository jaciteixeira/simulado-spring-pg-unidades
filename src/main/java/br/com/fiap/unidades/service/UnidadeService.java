package br.com.fiap.unidades.service;

import br.com.fiap.unidades.dto.request.UnidadeRequest;
import br.com.fiap.unidades.dto.response.UnidadeResponse;
import br.com.fiap.unidades.entity.Unidade;
import br.com.fiap.unidades.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UnidadeService implements ServiceDTO<Unidade, UnidadeRequest, UnidadeResponse>{

    @Autowired
    private UnidadeRepository unidadeRepo;

    @Override
    public Unidade toEntity(UnidadeRequest r) {
        if (Objects.isNull(r)) return null;

        Unidade.UnidadeBuilder unidadeBuilder = Unidade.builder()
                .sigla(r.sigla())
                .nome(r.nome())
                .descricao(r.descricao());

        if (Objects.nonNull(r.macro())) {
            Unidade macro = findById(r.macro().id());
            unidadeBuilder.macro(macro);
        }

        return unidadeBuilder.build();
    }


    @Override
    public UnidadeResponse toResponse(Unidade e) {
        if (e == null) return null;

        UnidadeResponse.UnidadeResponseBuilder responseBuilder = UnidadeResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .sigla(e.getSigla())
                .descricao(e.getDescricao());

        if (Objects.nonNull(e.getMacro())) {
            responseBuilder.macro(toResponse(findById(e.getMacro().getId())));
        }

        return responseBuilder.build();
    }


    @Override
    public List<Unidade> findAll() {
        return unidadeRepo.findAll();
    }

    @Override
    public List<Unidade> findAll(Example<Unidade> example) {
        return unidadeRepo.findAll(example);
    }

    @Override
    public Unidade findById(Long id) {
        return unidadeRepo.findById(id).orElse(null);
    }

    @Override
    public Unidade save(Unidade e) {
        return unidadeRepo.save(e);
    }
}
