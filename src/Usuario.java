//Aluno: Arthur Mendes Lucas
//Curso: Análise e Desenvolvimento de Sistemas
//Período: 3º Semestre

//Classe para representar um usuário na fila de espera
public class Usuario {
    private String nome;
    private String contato;

    public Usuario(String nome, String contato) {
        this.nome = nome;
        this.contato = contato;
    }

    public String getNome() {
        return nome;
    }

    public String getContato() {
        return contato;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Contato: " + contato;
    }
}