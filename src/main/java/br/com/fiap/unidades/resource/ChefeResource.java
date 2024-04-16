package br.com.fiap.unidades.resource;

import br.com.fiap.unidades.dto.request.ChefeRequest;
import br.com.fiap.unidades.dto.request.PessoaRequest;
import br.com.fiap.unidades.dto.response.ChefeResponse;
import br.com.fiap.unidades.dto.response.PessoaResponse;
import br.com.fiap.unidades.entity.Pessoa;
import br.com.fiap.unidades.service.ChefeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController

public class ChefeResource implements ResourceDTO<ChefeRequest, ChefeResponse> {

    @Autowired
    private ChefeService service;

    @GetMapping()
    public ResponseEntity<Collection<ChefeResponse>> findAll(
            @RequestParam(name = "usuarioId", required = false) String usuario,
            @RequestParam(name = "substituto", required = false) String subistituto,
            @RequestParam(name = "unidadeId", required = false) String unidade
    ){
        var pessoa = Pessoa.builder()

                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withMatcher("dadosCliente.nome", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Pessoa> example = Example.of(pessoa, matcher);

        List<Pessoa> pesssoas = service.findAll(example);

        return ResponseEntity.ok(pesssoas.stream().map(service::toResponse).toList());
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<ChefeResponse> findById(@PathVariable Long id) {
        Pessoa pessoa = service.findById(id);
        if(pessoa == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.toResponse(pessoa));
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<ChefeResponse> save(ChefeRequest r) {
        Pessoa saved = service.save(service.toEntity(r));
        var response = service.toResponse( saved );
        if (response == null) return ResponseEntity.notFound().build();

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path( "/{id}" )
                .buildAndExpand( response.id() )
                .toUri();

        return ResponseEntity.created( uri ).body( response );
    }
}
