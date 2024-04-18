package br.com.fiap.unidades.service;

import br.com.fiap.unidades.dto.request.ChefeRequest;
import br.com.fiap.unidades.dto.response.ChefeResponse;
import br.com.fiap.unidades.entity.Chefe;
import br.com.fiap.unidades.repository.ChefeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ChefeService implements ServiceDTO<Chefe, ChefeRequest, ChefeResponse>{

    @Autowired
    private ChefeRepository chefeRepo;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UnidadeService unidadeService;

    @Override
    public Chefe toEntity(ChefeRequest r) {
        var usuario = usuarioService.findById(r.usuario().id());
        var unidade = unidadeService.findById(r.unidade().id());

        if(Objects.isNull(usuario) || Objects.isNull(unidade)) {
            return null;
        }
        return Chefe.builder()
                .fim(r.fim())
                .inicio(r.inicio())
                .usuario(usuario)
                .unidade(unidade)
                .substituto(r.substituto())
                .build();
    }

    @Override
    public ChefeResponse toResponse(Chefe e) {
        var usuario = usuarioService.findById(e.getUsuario().getId());
        var unidade = unidadeService.findById(e.getUnidade().getId());

        if(Objects.isNull(usuario) || Objects.isNull(unidade)) {
            return null;
        }
        return ChefeResponse.builder()
                .id(e.getId())
                .fim(e.getFim())
                .inicio(e.getInicio())
                .substituto(e.getSubstituto())
                .unidade(unidadeService.toResponse(unidade))
                .usuario(usuarioService.toResponse(usuario))
                .build();
    }

    @Override
    public List<Chefe> findAll() {
        return chefeRepo.findAll();
    }

    @Override
    public List<Chefe> findAll(Example<Chefe> example) {
        return chefeRepo.findAll(example);
    }

    @Override
    public Chefe findById(Long id) {
        return chefeRepo.findById(id).orElse(null);
    }

    @Override
    public Chefe save(Chefe e) {
        return chefeRepo.save(e);
    }
}
