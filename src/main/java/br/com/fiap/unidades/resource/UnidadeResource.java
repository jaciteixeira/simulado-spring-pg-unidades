package br.com.fiap.unidades.resource;

import br.com.fiap.unidades.dto.response.UnidadeResponse;
import br.com.fiap.unidades.dto.request.UnidadeRequest;
import br.com.fiap.unidades.entity.Unidade;
import br.com.fiap.unidades.service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/unidade")
public class UnidadeResource implements ResourceDTO<UnidadeRequest, UnidadeResponse>{

    @Autowired
    private UnidadeService service;

    @GetMapping()
    public ResponseEntity<List<UnidadeResponse>> findAll(
            @RequestParam(name="nome", required = false) String nome,
            @RequestParam(name="sigla", required = false) String sigla,
            @RequestParam(name="macroId", required = false) Long macroId
    ) {

        Unidade.UnidadeBuilder unidadeBuilder = Unidade.builder()
                .nome(nome)
                .sigla(sigla);

        if (!Objects.isNull(macroId)) {
            unidadeBuilder.macro(Unidade.builder().id(macroId).build());
        }

        Unidade unidade = unidadeBuilder.build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Unidade> example = Example.of(unidade, matcher);

        List<UnidadeResponse> list = service.findAll(example)
                .stream()
                .map(service::toResponse).toList();

        if(list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<UnidadeResponse> findById(@PathVariable Long id) {
        UnidadeResponse response = service.toResponse(service.findById(id));
        if(Objects.isNull(response)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<UnidadeResponse> save(@RequestBody @Valid UnidadeRequest request) {
        Unidade entity = service.toEntity(request);
        if (Objects.isNull(entity)) return ResponseEntity.badRequest().build();

        Unidade saved = service.save(entity);
        UnidadeResponse response = service.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
