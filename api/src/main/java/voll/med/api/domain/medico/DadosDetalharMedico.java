package voll.med.api.domain.medico;

import voll.med.api.domain.endereco.Endereco;

public record DadosDetalharMedico (String nome, String email, String telefone, String crm, Especialidade especialidade, Endereco endereco) {

    public DadosDetalharMedico(Medico medico) {
        this(medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
    }

}
