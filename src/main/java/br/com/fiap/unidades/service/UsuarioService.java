package br.com.fiap.unidades.service;

import br.com.fiap.unidades.dto.request.UsuarioRequest;
import br.com.fiap.unidades.dto.response.UsuarioResponse;
import br.com.fiap.unidades.entity.Usuario;
import br.com.fiap.unidades.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService implements ServiceDTO<Usuario, UsuarioRequest, UsuarioResponse> {

    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private PessoaService pessoaService;

    @Override
    public Usuario toEntity(UsuarioRequest r) {
        if (Objects.isNull(r)) return null;
        return Usuario.builder()
                .pessoa(pessoaService.toEntity(r.pessoa()))
                .username(r.username())
                .password(r.password())
                .build();
    }

    @Override
    public UsuarioResponse toResponse(Usuario e) {
        if (Objects.isNull(e)) return null;
        return UsuarioResponse.builder()
                .id(e.getId())
                .username(e.getUsername())
                .pessoa(pessoaService.toResponse(e.getPessoa()))
                .build();
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepo.findAll();
    }

    @Override
    public List<Usuario> findAll(Example<Usuario> example) {
        return usuarioRepo.findAll(example);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepo.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario e) {
        return usuarioRepo.save(e);
    }
}
