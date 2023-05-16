package voll.med.api.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import voll.med.api.domain.medico.*;

import java.net.URI;
import java.util.Optional;
@Log4j2
@RestController
@RequestMapping(path = "medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;
    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastrarMedico dadosMedico, UriComponentsBuilder uriBuilder) {

        log.info("Iniciando endpoint [Cadastrar]");

        Medico medico = new Medico(dadosMedico);
        medicoRepository.save(medico);

        //Retorna no cabeçalho da requisição o Location com a url completa do endpoint com o id gerado no cadastro
        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(medico);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(sort = {"nome"}) Pageable pageable) {

        log.info("Iniciando endpoint [Listar]");
        Page<DadosListagemMedico> page = medicoRepository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalharMedico> consultar(@PathVariable Long id) {

        log.info("Iniciando endpoint [Consultar]");
        Optional<Medico> medico = medicoRepository.findByIdAndAtivoTrue(id);

        if (medico.isPresent()) {
            return new ResponseEntity<>(new DadosDetalharMedico(medico.get()), HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosListagemMedico> atualizar(@RequestBody @Valid DadosAtualizarMedico dadosMedico) {

        log.info("Iniciando endpoint [Atualizar]");
        Optional<Medico> medico = medicoRepository.findById(dadosMedico.id());

        if (medico.isPresent()){
            medico.get().atualizarDadosMedico(dadosMedico);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        log.info("Iniciando endpoint [Deletar]");
        medicoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/inativar")
    @Transactional
    public ResponseEntity<String> inativar(@PathVariable Long id) {

        log.info("Iniciando endpoint [Inativar]");
        Optional<Medico> medico = medicoRepository.findById(id);

        if (medico.isPresent()) {
            medico.get().inativar();
            return new ResponseEntity("Médico inativado com sucesso!", HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
}
