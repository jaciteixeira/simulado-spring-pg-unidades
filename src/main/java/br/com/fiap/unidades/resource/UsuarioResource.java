package br.com.fiap.unidades.resource;

import br.com.fiap.unidades.dto.request.UsuarioRequest;
import br.com.fiap.unidades.dto.response.UsuarioResponse;
import br.com.fiap.unidades.entity.Pessoa;
import br.com.fiap.unidades.entity.Tipo;
import br.com.fiap.unidades.entity.Usuario;
import br.com.fiap.unidades.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource implements ResourceDTO<UsuarioRequest, UsuarioResponse>{

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll(
            @RequestParam(name="username", required = false) String username,
            @RequestParam(name="pessoaId", required = false) Long pessoaId,
            @RequestParam(name="pessoaNome", required = false) String pessoaNome,
            @RequestParam(name="pessoaSobrenome", required = false) String pessoaSobrenome,
            @RequestParam(name="pessoaNascimento", required = false) LocalDate pessoaNascimento,
            @RequestParam(name="pessoaTipo", required = false) String pessoaTipo,
            @RequestParam(name="pessoaEmail", required = false) String pessoaEmail
    ) {
        Pessoa pessoa = Pessoa.builder().id(pessoaId).nome(pessoaNome).sobrenome(pessoaSobrenome).nascimento(pessoaNascimento).tipo(Tipo.valueOf(pessoaTipo)).email(pessoaEmail).build();
        Usuario usuario = Usuario.builder().username(username).pessoa(pessoa).build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withMatcher("pessoaNome.nome", ExampleMatcher.GenericPropertyMatchers.contains());;

        Example<Usuario> example = Example.of(usuario, matcher);

        List<UsuarioResponse> list = service.findAll(example)
                .stream()
                .map(service::toResponse).toList();

        return ResponseEntity.ok(list);

    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {

        UsuarioResponse response = service.toResponse(service.findById(id));
        if (response == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<UsuarioResponse> save(@RequestBody @Valid UsuarioRequest r) {
        Usuario entity = service.toEntity(r);
        Usuario saved = service.save(entity);
        UsuarioResponse response = service.toResponse(saved);
        System.out.println(response);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
