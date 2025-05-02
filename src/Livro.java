//Aluno: Arthur Mendes Lucas
//Curso: Análise e Desenvolvimento de Sistemas
//Período: 3º Semestre

//Classe para representar um livro
public class Livro {
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private boolean emprestado;
    private String genero;

//Construtor
    public Livro(String titulo, String autor, int anoPublicacao, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.emprestado = false;
        this.genero = genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public boolean isEmprestado() {
        return emprestado;
    }

    public void setEmprestado(boolean emprestado) {
        this.emprestado = emprestado;
    }

    public String getGenero() {
        return genero;
    }

//Metodo para mostrar detalhes do livro
    public void mostrarDetalhes() {
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Ano: " + anoPublicacao);
        System.out.println("Gênero: " + genero);
        System.out.println("Status: " + (emprestado ? "Emprestado" : "Disponível"));
        System.out.println("--------------------");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Livro livro = (Livro) obj;
        if (titulo == null) {
            if (livro.titulo != null) return false;
        } else if (!titulo.equals(livro.titulo)) return false;
        if (autor == null) {
            if (livro.autor != null) return false;
        } else if (!autor.equals(livro.autor)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + ((titulo == null) ? 0 : titulo.hashCode());
        result = 31 * result + ((autor == null) ? 0 : autor.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return titulo + " por " + autor + " (" + anoPublicacao + ")";
    }
}