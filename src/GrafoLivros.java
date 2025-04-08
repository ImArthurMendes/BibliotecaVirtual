//Aluno: Arthur Mendes Lucas
//Curso: Análise e Desenvolvimento de Sistemas
//Período: 3º Semestre

//Classe para gerenciar o grafo de livros e recomendações
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class GrafoLivros {
//HashMap para representar o grafo
    private HashMap<Livro, Set<Livro>> grafo;

    public GrafoLivros() {
        grafo = new HashMap<>();
    }

//Adicionar um livro ao grafo
    public void adicionarLivro(Livro livro) {
        if (!grafo.containsKey(livro)) {
            grafo.put(livro, new HashSet<Livro>());
        }
    }

//Adicionar uma recomendação entre dois livros
    public void adicionarRecomendacao(Livro livro, Livro recomendacao) {
        adicionarLivro(livro);
        adicionarLivro(recomendacao);

//Adicionar recomendação
        grafo.get(livro).add(recomendacao);
    }

//Obter todas as recomendações para um livro
    public Set<Livro> obterRecomendacoes(Livro livro) {
        return grafo.getOrDefault(livro, new HashSet<>());
    }

//Inicializar o grafo com alguns livros e recomendações
    public void inicializarGrafo() {
        Livro livro1 = new Livro("O Hobbit", "J.R.R. Tolkien", 1937, "Fantasia");
        Livro livro2 = new Livro("Diário de um Banana", "Jeff Kinney", 2007, "Infantojuvenil");
        Livro livro3 = new Livro("Pai Rico, Pai Pobre", "Robert Kiyosaki", 1997, "Finanças");
        Livro livro4 = new Livro("Sapiens: Uma Breve História da Humanidade", "Yuval Noah Harari", 2011, "História");
        Livro livro5 = new Livro("Harry Potter e a Pedra Filosofal", "J.K. Rowling", 1997, "Fantasia");
        Livro livro6 = new Livro("A Culpa é das Estrelas", "John Green", 2012, "Romance Jovem Adulto");
        Livro livro7 = new Livro("Os Jogos da Fome", "Suzanne Collins", 2008, "Distopia");
        Livro livro8 = new Livro("Percy Jackson e o Ladrão de Raios", "Rick Riordan", 2005, "Fantasia");
        Livro livro9 = new Livro("O Poder do Hábito", "Charles Duhigg", 2012, "Autoajuda");
        Livro livro10 = new Livro("A Sutil Arte de Ligar o Foda-se", "Mark Manson", 2016, "Autoajuda");
        Livro livro11 = new Livro("As Crônicas de Gelo e Fogo: A Guerra dos Tronos", "George R.R. Martin", 1996, "Fantasia Épica");

//Adicionar livros ao grafo
        adicionarLivro(livro1);
        adicionarLivro(livro2);
        adicionarLivro(livro3);
        adicionarLivro(livro4);
        adicionarLivro(livro5);
        adicionarLivro(livro6);
        adicionarLivro(livro7);
        adicionarLivro(livro8);
        adicionarLivro(livro9);
        adicionarLivro(livro10);
        adicionarLivro(livro11);

//Adicionar recomendações
        adicionarRecomendacao(livro1, livro5);  // O Hobbit -> Harry Potter
        adicionarRecomendacao(livro1, livro11); // O Hobbit -> A Guerra dos Tronos
        adicionarRecomendacao(livro1, livro8);  // O Hobbit -> Percy Jackson
//Diário de um Banana
        adicionarRecomendacao(livro2, livro8);  // Diário de um Banana -> Percy Jackson
        adicionarRecomendacao(livro2, livro6);  // Diário de um Banana -> A Culpa é das Estrelas
//Pai Rico, Pai Pobre
        adicionarRecomendacao(livro3, livro9);  // Pai Rico, Pai Pobre -> O Poder do Hábito
        adicionarRecomendacao(livro3, livro10); // Pai Rico, Pai Pobre -> A Sutil Arte de Ligar o Foda-se
        adicionarRecomendacao(livro3, livro4);  // Pai Rico, Pai Pobre -> Sapiens
//Sapiens
        adicionarRecomendacao(livro4, livro3);  // Sapiens -> Pai Rico, Pai Pobre
        adicionarRecomendacao(livro4, livro9);  // Sapiens -> O Poder do Hábito
//Harry Potter
        adicionarRecomendacao(livro5, livro1);  // Harry Potter -> O Hobbit
        adicionarRecomendacao(livro5, livro8);  // Harry Potter -> Percy Jackson
        adicionarRecomendacao(livro5, livro11); // Harry Potter -> A Guerra dos Tronos
//A Culpa é das Estrelas
        adicionarRecomendacao(livro6, livro2);  // A Culpa é das Estrelas -> Diário de um Banana
        adicionarRecomendacao(livro6, livro7);  // A Culpa é das Estrelas -> Os Jogos da Fome
//Os Jogos da Fome
        adicionarRecomendacao(livro7, livro11); // Os Jogos da Fome -> A Guerra dos Tronos
        adicionarRecomendacao(livro7, livro6);  // Os Jogos da Fome -> A Culpa é das Estrelas
        adicionarRecomendacao(livro7, livro8);  // Os Jogos da Fome -> Percy Jackson
//Percy Jackson
        adicionarRecomendacao(livro8, livro5);  // Percy Jackson -> Harry Potter
        adicionarRecomendacao(livro8, livro1);  // Percy Jackson -> O Hobbit
//O Poder do Hábito
        adicionarRecomendacao(livro9, livro10); // O Poder do Hábito -> A Sutil Arte de Ligar o Foda-se
        adicionarRecomendacao(livro9, livro3);  // O Poder do Hábito -> Pai Rico, Pai Pobre
//A Sutil Arte de Ligar o Foda-se
        adicionarRecomendacao(livro10, livro9); // A Sutil Arte de Ligar o Foda-se -> O Poder do Hábito
        adicionarRecomendacao(livro10, livro3); // A Sutil Arte de Ligar o Foda-se -> Pai Rico, Pai Pobre
//A Guerra dos Tronos
        adicionarRecomendacao(livro11, livro1); // A Guerra dos Tronos -> O Hobbit
        adicionarRecomendacao(livro11, livro7); // A Guerra dos Tronos -> Os Jogos da Fome
    }

//MEtodo para recomendar livros com base em um livro que o usuário gostou
    public List<Livro> recomendarLivros(Livro livroReferencia) {
        List<Livro> recomendacoes = new ArrayList<>();

        Set<Livro> livrosRecomendados = grafo.get(livroReferencia);

        if (livrosRecomendados != null) {
            recomendacoes.addAll(livrosRecomendados);
        }

        return recomendacoes;
    }

//Metodo para listar todos os livros cadastrados no grafo
    public Set<Livro> listarTodosLivros() {
        return grafo.keySet();
    }

//Metodo para encontrar livros por gênero
    public List<Livro> encontrarLivrosPorGenero(String genero) {
        List<Livro> livrosDoGenero = new ArrayList<>();

        for (Livro livro : grafo.keySet()) {
            if (livro.getGenero().equalsIgnoreCase(genero)) {
                livrosDoGenero.add(livro);
            }
        }

        return livrosDoGenero;
    }
}