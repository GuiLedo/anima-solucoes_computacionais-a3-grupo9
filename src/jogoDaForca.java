import java.util.Scanner;

public class jogoDaForca {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] palavras = {"JAVA", "PROGRAMACAO", "DESENVOLVIMENTO", "ALGORITMO", "COMPUTADOR"};
        String palavraEscolhida = palavras[(int) (Math.random() * palavras.length)];
        StringBuilder palavraAdivinhada = new StringBuilder("_".repeat(palavraEscolhida.length()));
        int tentativas = 6;
        boolean jogoEmAndamento = true;

        System.out.println("Bem-vindo ao Jogo da Forca!");
        
        while (jogoEmAndamento) {
            System.out.println("\nPalavra: " + palavraAdivinhada);
            System.out.println("Você tem " + tentativas + " tentativas restantes.");
            System.out.print("Digite uma letra: ");
            char letra = scanner.next().toUpperCase().charAt(0);

            if (palavraEscolhida.indexOf(letra) >= 0) {
                for (int i = 0; i < palavraEscolhida.length(); i++) {
                    if (palavraEscolhida.charAt(i) == letra) {
                        palavraAdivinhada.setCharAt(i, letra);
                    }
                }
                System.out.println("Boa! A letra '" + letra + "' está na palavra.");
            } else {
                tentativas--;
                System.out.println("Ops! A letra '" + letra + "' não está na palavra.");
            }

            if (palavraAdivinhada.toString().equals(palavraEscolhida)) {
                System.out.println("\nParabéns! Você adivinhou a palavra: " + palavraEscolhida);
                jogoEmAndamento = false;
            } else if (tentativas == 0) {
                System.out.println("\nVocê perdeu! A palavra era: " + palavraEscolhida);
                jogoEmAndamento = false;
            }
        }

        scanner.close();
    }
}