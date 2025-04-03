import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

// Classe que representa um Livro
class Livro {
    String titulo;
    String autor;
    int anoPublicacao;
    boolean emprestado;

    // Construtor
    Livro(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.emprestado = false;
    }

    // Método para mostrar os detalhes do livro
    void mostrarDetalhes() {
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Ano: " + anoPublicacao);
        System.out.println("Status: " + (emprestado ? "Emprestado" : "Disponível"));
        System.out.println("--------------------");
    }
}

// Classe para representar um usuário na fila de espera
class Usuario {
    String nome;
    String contato;

    Usuario(String nome, String contato) {
        this.nome = nome;
        this.contato = contato;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Contato: " + contato;
    }
}

// Classe Principal - Biblioteca Virtual
public class BibliotecaVirtual {
    // Lista para armazenar os livros
    static LinkedList<Livro> listaLivros = new LinkedList<>();

    // Fila para gerenciar lista de espera de livros
    static Queue<Usuario> filaEspera = new LinkedList<>();

    // Pilha para armazenar histórico de navegação
    static Stack<Livro> historicoNavegacao = new Stack<>();

    // Scanner para leitura de entrada do usuário
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Adicionar alguns livros de exemplo
        inicializarBiblioteca();

        // Mostrar menu principal
        menuPrincipal();
    }

    // Inicializar a biblioteca com alguns livros
    static void inicializarBiblioteca() {
        listaLivros.add(new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", 1943));
        listaLivros.add(new Livro("Dom Quixote", "Miguel de Cervantes", 1605));
        listaLivros.add(new Livro("Harry Potter", "J.K. Rowling", 1997));
    }

    // Menu principal interativo
    static void menuPrincipal() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== BIBLIOTECA VIRTUAL =====");
            System.out.println("1. Adicionar novo livro");
            System.out.println("2. Listar todos os livros");
            System.out.println("3. Consultar detalhes de um livro");
            System.out.println("4. Emprestar livro");
            System.out.println("5. Devolver livro");
            System.out.println("6. Entrar na fila de espera");
            System.out.println("7. Ver fila de espera");
            System.out.println("8. Ver histórico de navegação");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer

            switch (opcao) {
                case 1:
                    adicionarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    consultarLivro();
                    break;
                case 4:
                    emprestarLivro();
                    break;
                case 5:
                    devolverLivro();
                    break;
                case 6:
                    entrarFilaEspera();
                    break;
                case 7:
                    verFilaEspera();
                    break;
                case 8:
                    verHistoricoNavegacao();
                    break;
                case 0:
                    System.out.println("Encerrando o programa. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    // Adicionar um novo livro à biblioteca
    static void adicionarLivro() {
        System.out.println("\n--- ADICIONAR NOVO LIVRO ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("Ano de publicação: ");
        int ano = scanner.nextInt();
        scanner.nextLine();  // Limpar o buffer

        Livro novoLivro = new Livro(titulo, autor, ano);
        listaLivros.add(novoLivro);

        System.out.println("Livro adicionado com sucesso!");
    }

    // Listar todos os livros da biblioteca
    static void listarLivros() {
        System.out.println("\n--- LISTA DE LIVROS ---");

        if (listaLivros.isEmpty()) {
            System.out.println("A biblioteca está vazia!");
            return;
        }

        for (int i = 0; i < listaLivros.size(); i++) {
            System.out.println((i + 1) + ". " + listaLivros.get(i).titulo +
                    " (" + (listaLivros.get(i).emprestado ? "Emprestado" : "Disponível") + ")");
        }
    }

    // Consultar detalhes de um livro específico
    static void consultarLivro() {
        listarLivros();

        if (listaLivros.isEmpty()) {
            return;
        }

        System.out.print("\nDigite o número do livro para consultar: ");
        int indice = scanner.nextInt();
        scanner.nextLine();  // Limpar o buffer

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livroConsultado = listaLivros.get(indice - 1);
            System.out.println("\n--- DETALHES DO LIVRO ---");
            livroConsultado.mostrarDetalhes();

            // Adicionar ao histórico de navegação
            historicoNavegacao.push(livroConsultado);
            System.out.println("(Livro adicionado ao histórico de navegação)");
        } else {
            System.out.println("Índice inválido!");
        }
    }

    // Emprestar um livro
    static void emprestarLivro() {
        listarLivros();

        if (listaLivros.isEmpty()) {
            return;
        }

        System.out.print("\nDigite o número do livro para emprestar: ");
        int indice = scanner.nextInt();
        scanner.nextLine();  // Limpar o buffer

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livro = listaLivros.get(indice - 1);

            if (livro.emprestado) {
                System.out.println("Este livro já está emprestado!");
            } else {
                livro.emprestado = true;
                System.out.println("Livro '" + livro.titulo + "' emprestado com sucesso!");
            }
        } else {
            System.out.println("Índice inválido!");
        }
    }

    // Devolver um livro
    static void devolverLivro() {
        System.out.println("\n--- DEVOLVER LIVRO ---");

        // Listar apenas livros emprestados
        boolean temLivrosEmprestados = false;
        for (int i = 0; i < listaLivros.size(); i++) {
            if (listaLivros.get(i).emprestado) {
                if (!temLivrosEmprestados) {
                    System.out.println("Livros emprestados:");
                    temLivrosEmprestados = true;
                }
                System.out.println((i + 1) + ". " + listaLivros.get(i).titulo);
            }
        }

        if (!temLivrosEmprestados) {
            System.out.println("Não há livros emprestados no momento.");
            return;
        }

        System.out.print("Digite o número do livro para devolver: ");
        int indice = scanner.nextInt();
        scanner.nextLine();  // Limpar o buffer

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livro = listaLivros.get(indice - 1);

            if (livro.emprestado) {
                livro.emprestado = false;
                System.out.println("Livro '" + livro.titulo + "' devolvido com sucesso!");

                // Notificar próximo usuário na fila de espera
                if (!filaEspera.isEmpty()) {
                    Usuario proximoUsuario = filaEspera.poll();
                    System.out.println("O usuário " + proximoUsuario.nome +
                            " foi notificado que o livro está disponível!");
                }
            } else {
                System.out.println("Este livro não está emprestado!");
            }
        } else {
            System.out.println("Índice inválido!");
        }
    }

    // Entrar na fila de espera para um livro
    static void entrarFilaEspera() {
        listarLivros();

        if (listaLivros.isEmpty()) {
            return;
        }

        System.out.print("\nDigite o número do livro que deseja entrar na fila de espera: ");
        int indice = scanner.nextInt();
        scanner.nextLine();  // Limpar o buffer

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livro = listaLivros.get(indice - 1);

            if (!livro.emprestado) {
                System.out.println("Este livro está disponível! Você pode emprestá-lo agora.");
                return;
            }

            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();

            System.out.print("Digite seu contato: ");
            String contato = scanner.nextLine();

            Usuario usuario = new Usuario(nome, contato);
            filaEspera.add(usuario);

            System.out.println("Você entrou na fila de espera para o livro '" + livro.titulo + "'");
        } else {
            System.out.println("Índice inválido!");
        }
    }

    // Ver a fila de espera atual
    static void verFilaEspera() {
        System.out.println("\n--- FILA DE ESPERA ---");

        if (filaEspera.isEmpty()) {
            System.out.println("A fila de espera está vazia!");
            return;
        }

        int posicao = 1;
        for (Usuario usuario : filaEspera) {
            System.out.println(posicao + ". " + usuario);
            posicao++;
        }
    }

    // Ver histórico de navegação
    static void verHistoricoNavegacao() {
        System.out.println("\n--- HISTÓRICO DE NAVEGAÇÃO ---");

        if (historicoNavegacao.isEmpty()) {
            System.out.println("O histórico de navegação está vazio!");
            return;
        }

        System.out.println("Livros consultados recentemente (do mais recente para o mais antigo):");

        // Criar uma cópia da pilha para não perder os dados originais
        Stack<Livro> copiaHistorico = new Stack<>();
        copiaHistorico.addAll(historicoNavegacao);

        int posicao = 1;
        while (!copiaHistorico.isEmpty()) {
            Livro livro = copiaHistorico.pop();
            System.out.println(posicao + ". " + livro.titulo + " por " + livro.autor);
            posicao++;
        }
    }
}