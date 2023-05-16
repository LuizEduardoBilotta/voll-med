package voll.med.api.domain.medico;

import jakarta.persistence.*;
import lombok.*;
import voll.med.api.domain.endereco.Endereco;

import java.util.Objects;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Medico(DadosCadastrarMedico dadosMedico) {
        this.nome = dadosMedico.nome();
        this.email = dadosMedico.email();
        this.telefone = dadosMedico.telefone();
        this.crm = dadosMedico.crm();
        this.especialidade = dadosMedico.especialidade();
        this.endereco = new Endereco(dadosMedico.endereco());
        this.ativo = true;
    }

    public void atualizarDadosMedico(DadosAtualizarMedico dadosMedico) {
        this.nome = validateInformation(dadosMedico.nome(), this.getNome());
        this.telefone = validateInformation(dadosMedico.telefone(), this.getTelefone());

        if (dadosMedico.endereco() != null) {
            this.endereco.atualizarDadosEndereco(dadosMedico.endereco());
        }
    }

    private String validateInformation(String newInformation, String defaultInformation) {
        return Objects.requireNonNullElse(newInformation, defaultInformation);
    }

    public void inativar() {
        this.ativo = false;
    }
}
