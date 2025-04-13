import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class JogoDaForcaGUI extends JFrame {
    private JTextField inputLetra;
    private JTextArea areaTexto;
    private JButton botaoEnviar, botaoSalvar, botaoNovoJogo;

    private String[] palavras = {"JAVA", "PROGRAMACAO", "DESENVOLVIMENTO", "ALGORITMO", "COMPUTADOR"};
    private String palavraEscolhida;
    private StringBuilder palavraAdivinhada;
    private char[] letrasUsadas;
    private int tentativas;
    private int tamanhoLetrasUsadas;
    private boolean jogoEmAndamento;

    public JogoDaForcaGUI() {
        super("Jogo da Forca");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400); // Tamanho da janela ajustado
        setLayout(new BorderLayout());

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 16));
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Garantir espaçamento adequado

        inputLetra = new JTextField(5);
        botaoEnviar = new JButton("Enviar");
        botaoSalvar = new JButton("Salvar");
        botaoNovoJogo = new JButton("Novo Jogo");

        painelInferior.add(new JLabel("Letra:"));
        painelInferior.add(inputLetra);
        painelInferior.add(botaoEnviar);
        painelInferior.add(botaoSalvar);
        painelInferior.add(botaoNovoJogo);

        add(painelInferior, BorderLayout.SOUTH);

        botaoEnviar.addActionListener(e -> jogarRodada());
        botaoSalvar.addActionListener(e -> salvarJogo());
        botaoNovoJogo.addActionListener(e -> novoJogo());

        // Adicionando KeyListener para o campo de texto (inputLetra)
        inputLetra.addActionListener(e -> jogarRodada());

        carregarJogo();
        atualizarTela();

        setVisible(true);
    }

    private void novoJogo() {
        palavraEscolhida = palavras[(int) (Math.random() * palavras.length)];
        palavraAdivinhada = new StringBuilder("_".repeat(palavraEscolhida.length()));
        letrasUsadas = new char[26];
        tamanhoLetrasUsadas = 0;
        tentativas = 6;
        jogoEmAndamento = true;
        atualizarTela();

        // Apagar o arquivo de save ao iniciar um novo jogo
        File arquivo = new File("forca_save.txt");
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

    private void jogarRodada() {
        if (!jogoEmAndamento) return;

        String texto = inputLetra.getText().toUpperCase();
        inputLetra.setText("");

        if (texto.length() == 0) return;
        char letra = texto.charAt(0);

        if (letraJaUsada(letra)) {
            JOptionPane.showMessageDialog(this, "Letra já usada!");
            return;
        }

        letrasUsadas[tamanhoLetrasUsadas++] = letra;

        if (palavraEscolhida.indexOf(letra) >= 0) {
            for (int i = 0; i < palavraEscolhida.length(); i++) {
                if (palavraEscolhida.charAt(i) == letra) {
                    palavraAdivinhada.setCharAt(i, letra);
                }
            }
        } else {
            tentativas--;
        }

        if (palavraAdivinhada.toString().equals(palavraEscolhida)) {
            jogoEmAndamento = false;
            JOptionPane.showMessageDialog(this, "Parabéns! Você ganhou!");
        } else if (tentativas == 0) {
            jogoEmAndamento = false;
            JOptionPane.showMessageDialog(this, "Você perdeu! A palavra era: " + palavraEscolhida);
        }

        atualizarTela();
    }

    private boolean letraJaUsada(char letra) {
        for (int i = 0; i < tamanhoLetrasUsadas; i++) {
            if (letrasUsadas[i] == letra) return true;
        }
        return false;
    }

    private void atualizarTela() {
        StringBuilder sb = new StringBuilder();
        sb.append("Palavra: ");
        
        // Adiciona um espaço após cada caractere
        for (int i = 0; i < palavraAdivinhada.length(); i++) {
            sb.append(palavraAdivinhada.charAt(i)).append(" ");
        }
        
        sb.append("\n\n");
        sb.append("Tentativas restantes: ").append(tentativas).append("\n\n");
        sb.append("Letras usadas: ");
        for (int i = 0; i < tamanhoLetrasUsadas; i++) {
            sb.append(letrasUsadas[i]).append(" ");
        }
        areaTexto.setText(sb.toString());
    }

    private void salvarJogo() {
        try (PrintWriter writer = new PrintWriter("forca_save.txt")) {
            writer.println(palavraEscolhida);
            writer.println(palavraAdivinhada.toString());
            writer.println(tentativas);
            writer.println(tamanhoLetrasUsadas);
            for (int i = 0; i < tamanhoLetrasUsadas; i++) {
                writer.print(letrasUsadas[i]);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar jogo.");
        }
    }

    private void carregarJogo() {
        File arquivo = new File("forca_save.txt");
        if (!arquivo.exists()) {
            novoJogo();
            return;
        }

        try (Scanner scanner = new Scanner(arquivo)) {
            palavraEscolhida = scanner.nextLine();
            palavraAdivinhada = new StringBuilder(scanner.nextLine());
            tentativas = Integer.parseInt(scanner.nextLine());
            tamanhoLetrasUsadas = Integer.parseInt(scanner.nextLine());
            String usadas = scanner.nextLine();
            letrasUsadas = new char[26];
            for (int i = 0; i < usadas.length(); i++) {
                letrasUsadas[i] = usadas.charAt(i);
            }
            jogoEmAndamento = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar jogo. Novo jogo será iniciado.");
            novoJogo();
        }
    }

    // Herança e polimorfismo com classe abstrata simples
    abstract static class Jogo {
        abstract void iniciar();
    }

    static class ForcaSimples extends Jogo {
        public void iniciar() {
            new JogoDaForcaGUI();
        }
    }

    public static void main(String[] args) {
        new ForcaSimples().iniciar();
    }
}