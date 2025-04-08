//Aluno: Arthur Mendes Lucas
//Curso: Análise e Desenvolvimento de Sistemas
//Período: 3º Semestre
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.List;
import java.util.Set;

//Classe Principal - Biblioteca Virtual
public class BibliotecaVirtual {
//Lista para armazenar os livros
    private static LinkedList<Livro> listaLivros = new LinkedList<>();

//Fila para gerenciar lista de espera de livros
    private static Queue<Usuario> filaEspera = new LinkedList<>();

//Pilha para armazenar histórico de navegação
    private static Stack<Livro> historicoNavegacao = new Stack<>();

//Grafo para armazenar livros e suas recomendações
    private static GrafoLivros grafoLivros = new GrafoLivros();

//Scanner para leitura de entrada do usuário
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarBiblioteca();
        menuPrincipal();
        scanner.close();
    }

    private static void inicializarBiblioteca() {
        grafoLivros.inicializarGrafo();

        for (Livro livro : grafoLivros.listarTodosLivros()) {
            listaLivros.add(livro);
        }

        System.out.println("Biblioteca inicializada com " + listaLivros.size() + " livros.");
    }

//Menu principal
    private static void menuPrincipal() {
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
            System.out.println("9. Recomendar livros");
            System.out.println("10. Buscar livros por gênero");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

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
                case 9:
                    recomendarLivros();
                    break;
                case 10:
                    buscarLivrosPorGenero();
                    break;
                case 0:
                    System.out.println("Encerrando o programa. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

//Adicionar um novo livro
    private static void adicionarLivro() {
        System.out.println("\n--- ADICIONAR NOVO LIVRO ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("Ano de publicação: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Gênero: ");
        String genero = scanner.nextLine();

        Livro novoLivro = new Livro(titulo, autor, ano, genero);
        listaLivros.add(novoLivro);
        grafoLivros.adicionarLivro(novoLivro);

        System.out.println("Livro adicionado com sucesso!");

        System.out.print("Deseja adicionar recomendações para este livro? (S/N): ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            adicionarRecomendacoes(novoLivro);
        }
    }

    private static void adicionarRecomendacoes(Livro livro) {
        System.out.println("\n--- ADICIONAR RECOMENDAÇÕES ---");
        System.out.println("Selecione os livros para recomendar para: " + livro.getTitulo());

//Listar todos os livros
        listarLivros();

        System.out.println("Digite os números dos livros separados por vírgula (ou 0 para terminar): ");
        String entrada = scanner.nextLine();

        String[] indices = entrada.split(",");
        for (String indiceStr : indices) {
            int indice = Integer.parseInt(indiceStr.trim());

            if (indice > 0 && indice <= listaLivros.size()) {
                Livro livroRecomendado = listaLivros.get(indice - 1);
                grafoLivros.adicionarRecomendacao(livro, livroRecomendado);
                System.out.println("Recomendação adicionada: " + livroRecomendado.getTitulo());
            }
        }
    }

//Listar todos os livros da biblioteca
    private static void listarLivros() {
        System.out.println("\n--- LISTA DE LIVROS ---");

        if (listaLivros.isEmpty()) {
            System.out.println("A biblioteca está vazia!");
            return;
        }

        for (int i = 0; i < listaLivros.size(); i++) {
            System.out.println((i + 1) + ". " + listaLivros.get(i).getTitulo() +
                    " por " + listaLivros.get(i).getAutor() +
                    " (" + (listaLivros.get(i).isEmprestado() ? "Emprestado" : "Disponível") + ")");
        }
    }

//Consultar detalhes de um livro
    private static void consultarLivro() {
        listarLivros();

        if (listaLivros.isEmpty()) {
            return;
        }

        System.out.print("\nDigite o número do livro para consultar: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livroConsultado = listaLivros.get(indice - 1);
            System.out.println("\n--- DETALHES DO LIVRO ---");
            livroConsultado.mostrarDetalhes();

//Adicionar ao histórico de navegação
            historicoNavegacao.push(livroConsultado);
            System.out.println("(Livro adicionado ao histórico de navegação)");

//Mostrar recomendações para este livro
            System.out.println("\n--- RECOMENDAÇÕES PARA ESTE LIVRO ---");
            Set<Livro> recomendacoes = grafoLivros.obterRecomendacoes(livroConsultado);

            if (recomendacoes.isEmpty()) {
                System.out.println("Não há recomendações para este livro.");
            } else {
                int count = 1;
                for (Livro recomendado : recomendacoes) {
                    System.out.println(count + ". " + recomendado.getTitulo() + " por " + recomendado.getAutor());
                    count++;
                }
            }
        } else {
            System.out.println("Índice inválido!");
        }
    }

//Emprestar um livro
    private static void emprestarLivro() {
        listarLivros();

        if (listaLivros.isEmpty()) {
            return;
        }

        System.out.print("\nDigite o número do livro para emprestar: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livro = listaLivros.get(indice - 1);

            if (livro.isEmprestado()) {
                System.out.println("Este livro já está emprestado!");
            } else {
                livro.setEmprestado(true);
                System.out.println("Livro '" + livro.getTitulo() + "' emprestado com sucesso!");
            }
        } else {
            System.out.println("Índice inválido!");
        }
    }

//Devolver um livro
    private static void devolverLivro() {
        System.out.println("\n--- DEVOLVER LIVRO ---");

//Listar apenas livros emprestados
        boolean temLivrosEmprestados = false;
        for (int i = 0; i < listaLivros.size(); i++) {
            if (listaLivros.get(i).isEmprestado()) {
                if (!temLivrosEmprestados) {
                    System.out.println("Livros emprestados:");
                    temLivrosEmprestados = true;
                }
                System.out.println((i + 1) + ". " + listaLivros.get(i).getTitulo());
            }
        }

        if (!temLivrosEmprestados) {
            System.out.println("Não há livros emprestados no momento.");
            return;
        }

        System.out.print("Digite o número do livro para devolver: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livro = listaLivros.get(indice - 1);

            if (livro.isEmprestado()) {
                livro.setEmprestado(false);
                System.out.println("Livro '" + livro.getTitulo() + "' devolvido com sucesso!");

//Notificar próximo usuário na fila de espera
                if (!filaEspera.isEmpty()) {
                    Usuario proximoUsuario = filaEspera.poll();
                    System.out.println("O usuário " + proximoUsuario.getNome() +
                            " foi notificado que o livro está disponível!");
                }
            } else {
                System.out.println("Este livro não está emprestado!");
            }
        } else {
            System.out.println("Índice inválido!");
        }
    }

//Entrar na fila de espera para um livro
    private static void entrarFilaEspera() {
        listarLivros();

        if (listaLivros.isEmpty()) {
            return;
        }

        System.out.print("\nDigite o número do livro que deseja entrar na fila de espera: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livro = listaLivros.get(indice - 1);

            if (!livro.isEmprestado()) {
                System.out.println("Este livro está disponível! Você pode emprestá-lo agora.");
                return;
            }

            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();

            System.out.print("Digite seu contato: ");
            String contato = scanner.nextLine();

            Usuario usuario = new Usuario(nome, contato);
            filaEspera.add(usuario);

            System.out.println("Você entrou na fila de espera para o livro '" + livro.getTitulo() + "'");
        } else {
            System.out.println("Índice inválido!");
        }
    }

//Ver a fila de espera atual
    private static void verFilaEspera() {
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

//Ver histórico de navegação
    private static void verHistoricoNavegacao() {
        System.out.println("\n--- HISTÓRICO DE NAVEGAÇÃO ---");

        if (historicoNavegacao.isEmpty()) {
            System.out.println("O histórico de navegação está vazio!");
            return;
        }

        System.out.println("Livros consultados recentemente (do mais recente para o mais antigo):");

//Criar uma cópia da pilha para não perder os dados originais
        Stack<Livro> copiaHistorico = new Stack<>();
        copiaHistorico.addAll(historicoNavegacao);

        int posicao = 1;
        while (!copiaHistorico.isEmpty()) {
            Livro livro = copiaHistorico.pop();
            System.out.println(posicao + ". " + livro.getTitulo() + " por " + livro.getAutor());
            posicao++;
        }
    }

//Recomendar livros com base em um livro
    private static void recomendarLivros() {
        System.out.println("\n--- SISTEMA DE RECOMENDAÇÃO ---");

        listarLivros();

        if (listaLivros.isEmpty()) {
            return;
        }

        System.out.print("Digite o número do livro para obter recomendações: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livroReferencia = listaLivros.get(indice - 1);

            System.out.println("\nRecomendações baseadas em: " + livroReferencia.getTitulo());

            List<Livro> recomendacoes = grafoLivros.recomendarLivros(livroReferencia);

            if (recomendacoes.isEmpty()) {
                System.out.println("Não há recomendações para este livro.");
            } else {
                int count = 1;
                for (Livro recomendado : recomendacoes) {
                    System.out.println(count + ". " + recomendado.getTitulo() + " por " + recomendado.getAutor());
                    count++;
                }
            }
        } else {
            System.out.println("Índice inválido!");
        }
    }

//Buscar livros por genero
    private static void buscarLivrosPorGenero() {
        System.out.println("\n--- BUSCA POR GÊNERO ---");

        System.out.print("Digite o gênero desejado: ");
        String genero = scanner.nextLine();

        List<Livro> livrosDoGenero = grafoLivros.encontrarLivrosPorGenero(genero);

        if (livrosDoGenero.isEmpty()) {
            System.out.println("Não foram encontrados livros do gênero: " + genero);
        } else {
            System.out.println("\nLivros do gênero " + genero + ":");

            for (int i = 0; i < livrosDoGenero.size(); i++) {
                Livro livro = livrosDoGenero.get(i);
                System.out.println((i + 1) + ". " + livro.getTitulo() + " por " + livro.getAutor());
            }
        }
    }
}