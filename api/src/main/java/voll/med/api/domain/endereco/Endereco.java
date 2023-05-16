package voll.med.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

    public Endereco(DadosEndereco dadosEndereco) {
        this.logradouro = dadosEndereco.logradouro();
        this.bairro = dadosEndereco.bairro();
        this.cep = dadosEndereco.cep();
        this.numero = dadosEndereco.numero();
        this.complemento = dadosEndereco.complemento();
        this.cidade = dadosEndereco.cidade();
        this.uf = dadosEndereco.uf();
    }

    public void atualizarDadosEndereco(DadosEndereco endereco) {
        this.logradouro = validateInformation(endereco.logradouro(), this.getLogradouro());
        this.bairro = validateInformation(endereco.bairro(), this.getBairro());
        this.cep = validateInformation(endereco.cep(), this.getCep());
        this.numero = validateInformation(endereco.numero(), this.getNumero());
        this.complemento = validateInformation(endereco.complemento(), this.getComplemento());
        this.cidade = validateInformation(endereco.cidade(), this.getCidade());
        this.uf = validateInformation(endereco.uf(), this.getUf());
    }

    private String validateInformation(String newInformation, String defaultInformation) {
        return Objects.requireNonNullElse(newInformation, defaultInformation);
    }
}
