//Aluno: Arthur Mendes Lucas
//Curso: Análise e Desenvolvimento de Sistemas
//Período: 3º Semestre
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList; //ArrayList para ordenar distâncias
import java.util.Collections;
import java.util.Comparator;

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

//Limpa a lista antes de adicionar para evitar duplicatas
        listaLivros.clear();
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
            System.out.println("11. Ver recomendações por proximidade");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Erro: Por favor, digite um número.");
                scanner.next();
                opcao = -1;
                continue;
            }
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
                case 11:
                    verRecomendacoesPorProximidade();
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

        int ano = 0;
        boolean anoValido = false;
        while (!anoValido) {
            System.out.print("Ano de publicação: ");
            try {
                ano = scanner.nextInt();
                anoValido = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Erro: Por favor, digite um número válido para o ano.");
                scanner.next();
            }
        }
        scanner.nextLine();

        System.out.print("Gênero: ");
        String genero = scanner.nextLine();

        Livro novoLivro = new Livro(titulo, autor, ano, genero);
//Verifica se o livro já existe, baseado em título e autor
        if (listaLivros.contains(novoLivro)) {
            System.out.println("Aviso: Um livro com este título e autor já existe.");
        } else {
            listaLivros.add(novoLivro);
            grafoLivros.adicionarLivro(novoLivro);
            System.out.println("Livro adicionado com sucesso!");

            System.out.print("Deseja adicionar recomendações (conexões) para este livro? (S/N): ");
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("S")) {
                adicionarRecomendacoes(novoLivro);
            }
        }
    }

    private static void adicionarRecomendacoes(Livro livro) {
        System.out.println("\n--- ADICIONAR RECOMENDAÇÕES (CONEXÕES NO GRAFO) ---");
        System.out.println("Selecione os livros para conectar com: " + livro.getTitulo());

        listarLivros();

        if (listaLivros.size() <= 1) {
            System.out.println("Não há outros livros para conectar.");
            return;
        }

        System.out.println("Digite os números dos livros separados por vírgula (ex: 1, 3, 5) ou 0 para nenhum: ");
        String entrada = scanner.nextLine();

        if (entrada.trim().equals("0")) {
            System.out.println("Nenhuma recomendação adicionada.");
            return;
        }

        String[] indices = entrada.split(",");
        for (String indiceStr : indices) {
            try {
                int indice = Integer.parseInt(indiceStr.trim());
                if (indice > 0 && indice <= listaLivros.size()) {
                    Livro livroRecomendado = listaLivros.get(indice - 1);

                    if (!livro.equals(livroRecomendado)) {
                        grafoLivros.adicionarRecomendacao(livro, livroRecomendado);

                        System.out.println("Conexão adicionada: " + livro.getTitulo() + " -> " + livroRecomendado.getTitulo());
                    } else {
                        System.out.println("Aviso: Não é possível conectar um livro a ele mesmo (índice " + indice + ").");
                    }
                } else {
                    System.out.println("Aviso: Índice inválido ignorado: " + indice);
                }
            } catch (NumberFormatException e) {
                System.out.println("Aviso: Entrada inválida ignorada: " + indiceStr);
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
            Livro livroAtual = listaLivros.get(i);
            System.out.println((i + 1) + ". " + livroAtual.getTitulo() +
                    " por " + livroAtual.getAutor() +
                    " (" + (livroAtual.isEmprestado() ? "Emprestado" : "Disponível") + ")");
        }
    }

//Consultar detalhes de um livro
    private static void consultarLivro() {
        listarLivros();

        if (listaLivros.isEmpty()) {
            return;
        }

        System.out.print("\nDigite o número do livro para consultar: ");
        int indice = -1;
        try {
            indice = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Por favor, digite um número.");
            scanner.next();
            return;
        }
        scanner.nextLine();

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livroConsultado = listaLivros.get(indice - 1);
            System.out.println("\n--- DETALHES DO LIVRO ---");
            livroConsultado.mostrarDetalhes();

//Adicionar ao histórico de navegação
            historicoNavegacao.push(livroConsultado);
            System.out.println("(Livro adicionado ao histórico de navegação)");

//Mostrar recomendações para este livro
            System.out.println("\n--- RECOMENDAÇÕES DIRETAS (CONEXÕES) ---");
            Set<Livro> recomendacoes = grafoLivros.obterRecomendacoes(livroConsultado);

            if (recomendacoes == null || recomendacoes.isEmpty()) {
                System.out.println("Não há recomendações diretas para este livro.");
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
        int indice = -1;
        try {
            indice = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Por favor, digite um número.");
            scanner.next();
            return;
        }
        scanner.nextLine();

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livro = listaLivros.get(indice - 1);

            if (livro.isEmprestado()) {
                System.out.println("Este livro já está emprestado! Considere entrar na fila de espera (opção 6).");
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
        List<Livro> emprestados = new ArrayList<>();
        for (Livro livro : listaLivros) {
            if (livro.isEmprestado()) {
                emprestados.add(livro);
            }
        }

        if (emprestados.isEmpty()) {
            System.out.println("Não há livros emprestados no momento.");
            return;
        }

        System.out.println("Livros emprestados:");
        for (int i = 0; i < emprestados.size(); i++) {
            System.out.println((i + 1) + ". " + emprestados.get(i).getTitulo());
        }

        System.out.print("Digite o número do livro para devolver (da lista acima): ");
        int indiceListaEmprestados = -1;
        try {
            indiceListaEmprestados = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Por favor, digite um número.");
            scanner.next();
            return;
        }
        scanner.nextLine();

        if (indiceListaEmprestados > 0 && indiceListaEmprestados <= emprestados.size()) {
            Livro livroParaDevolver = emprestados.get(indiceListaEmprestados - 1);

            for(Livro livro : listaLivros) {
                if (livro.equals(livroParaDevolver)) {
                    if (livro.isEmprestado()) {
                        livro.setEmprestado(false);
                        System.out.println("Livro '" + livro.getTitulo() + "' devolvido com sucesso!");

                        if (!filaEspera.isEmpty()) {
                            Usuario proximoUsuario = filaEspera.poll();
                            System.out.println("Notificação: O usuário " + proximoUsuario.getNome() +
                                    " (primeiro da fila geral) foi notificado sobre a devolução.");
                        }
                    } else {
                        System.out.println("Erro interno: Livro selecionado não estava marcado como emprestado.");
                    }
                    return;
                }
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
        int indice = -1;
        try {
            indice = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Por favor, digite um número.");
            scanner.next();
            return;
        }
        scanner.nextLine();

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livro = listaLivros.get(indice - 1);

            if (!livro.isEmprestado()) {
                System.out.println("Este livro está disponível! Você pode emprestá-lo agora (opção 4).");
                return;
            }

            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();

            System.out.print("Digite seu contato (email ou telefone): ");
            String contato = scanner.nextLine();

            Usuario usuario = new Usuario(nome, contato);
            filaEspera.add(usuario);

            System.out.println(nome + ", você entrou na fila de espera geral. Será notificado quando um livro for devolvido.");
        } else {
            System.out.println("Índice inválido!");
        }
    }

//Ver a fila de espera atual
    private static void verFilaEspera() {
        System.out.println("\n--- FILA DE ESPERA (GERAL) ---");

        if (filaEspera.isEmpty()) {
            System.out.println("A fila de espera está vazia!");
            return;
        }

        System.out.println("Usuários aguardando (ordem de chegada):");
        int posicao = 1;

        for (Usuario usuario : new LinkedList<>(filaEspera)) {
            System.out.println(posicao + ". " + usuario);
            posicao++;
        }
    }

//Ver histórico de navegação
    private static void verHistoricoNavegacao() {
        System.out.println("\n--- HISTÓRICO DE NAVEGAÇÃO (LIVROS CONSULTADOS) ---");

        if (historicoNavegacao.isEmpty()) {
            System.out.println("O histórico de navegação está vazio!");
            return;
        }

        System.out.println("Livros consultados recentemente (do mais recente para o mais antigo):");

        Stack<Livro> copiaHistorico = (Stack<Livro>) historicoNavegacao.clone();

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

        System.out.print("Digite o número do livro para obter recomendações diretas: ");
        int indice = -1;
        try {
            indice = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Por favor, digite um número.");
            scanner.next();
            return;
        }
        scanner.nextLine();

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livroReferencia = listaLivros.get(indice - 1);

            System.out.println("\nRecomendações diretas: " + livroReferencia.getTitulo());

            Set<Livro> recomendacoes = grafoLivros.obterRecomendacoes(livroReferencia);

            if (recomendacoes == null || recomendacoes.isEmpty()) {
                System.out.println("Não há recomendações diretas para este livro.");
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
            System.out.println("\nLivros do gênero '" + genero + "':");

            for (int i = 0; i < livrosDoGenero.size(); i++) {
                Livro livro = livrosDoGenero.get(i);
                System.out.println((i + 1) + ". " + livro.getTitulo() + " por " + livro.getAutor());
            }
        }
    }

    // --- INÍCIO DA ATIVIDADE SOMATIVA 2 ---

    private static void verRecomendacoesPorProximidade() {
        System.out.println("\n--- RECOMENDAÇÕES POR PROXIMIDADE ---");

        listarLivros();

        if (listaLivros.isEmpty()) {
            return;
        }

        System.out.print("Digite o número do livro de referência para calcular as distâncias: ");
        int indice = -1;
        try {
            indice = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Por favor, digite um número.");
            scanner.next();
            return;
        }
        scanner.nextLine();

        if (indice > 0 && indice <= listaLivros.size()) {
            Livro livroOrigem = listaLivros.get(indice - 1);
            System.out.println("\nCalculando distâncias a partir de: " + livroOrigem.getTitulo() + "...");

            Map<Livro, Integer> distancias = grafoLivros.calcularDistancias(livroOrigem);

            if (distancias.isEmpty() && grafoLivros.listarTodosLivros().contains(livroOrigem)) {
                System.out.println("O livro selecionado não possui conexões com outros livros no grafo ou não foi encontrado internamente.");
            } else if (distancias.isEmpty()) {
                System.out.println("Livro de origem não encontrado no grafo.");
            } else {
                List<Map.Entry<Livro, Integer>> listaDistancias = new ArrayList<>(distancias.entrySet());

//Ordena a lista pela distância
                Collections.sort(listaDistancias, new Comparator<Map.Entry<Livro, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Livro, Integer> e1, Map.Entry<Livro, Integer> e2) {
                        return e1.getValue().compareTo(e2.getValue());
                    }
                });

                System.out.println("\nLivros ordenados por proximidade (menor distância primeiro):");
                for (Map.Entry<Livro, Integer> entry : listaDistancias) {
                    Livro livro = entry.getKey();
                    int distancia = entry.getValue();
                    if (!livro.equals(livroOrigem)) {
                        System.out.println("- " + livro.getTitulo() + " por " + livro.getAutor() + " (Distância: " + distancia + ")");
                    }
                }
            }
        } else {
            System.out.println("Índice inválido!");
        }
    }
}

