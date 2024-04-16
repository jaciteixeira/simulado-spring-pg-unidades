package br.com.fiap.unidades.resource;

import br.com.fiap.unidades.dto.request.PessoaRequest;
import br.com.fiap.unidades.dto.response.PessoaResponse;
import br.com.fiap.unidades.entity.Pessoa;
import br.com.fiap.unidades.service.PessoaService;
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
@RequestMapping(value = "/pessoa")
public class PessoaResource implements ResourceDTO<PessoaRequest, PessoaResponse>{

    @Autowired
    private PessoaService pessoaService;

    @GetMapping()
    public ResponseEntity<Collection<PessoaResponse>> findAll(
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

        List<Pessoa> pesssoas = pessoaService.findAll(example);

        return ResponseEntity.ok(pesssoas.stream().map(pessoaService::toResponse).toList());
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<PessoaResponse> findById(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.findById(id);
        if(pessoa == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pessoaService.toResponse(pessoa));
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<PessoaResponse> save(PessoaRequest r) {
        Pessoa saved = pessoaService.save(pessoaService.toEntity(r));
        var response = pessoaService.toResponse( saved );
        if (response == null) return ResponseEntity.notFound().build();

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path( "/{id}" )
                .buildAndExpand( response.id() )
                .toUri();

        return ResponseEntity.created( uri ).body( response );
    }
}
