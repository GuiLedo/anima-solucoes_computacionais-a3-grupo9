import java.util.Scanner; //! Importa a ferramenta Scanner para ler inputs do user

public class JogoDaForca { 

    public static void main(String[] args) { //! Botão "ligar" do código
        Scanner scanner = new Scanner(System.in); //! Criamos a variável scanner para toda vez que a usarmos, utilizarmos a ferramenta Scanner

        String[] palavras = { "JAVA", "PROGRAMACAO", "DESENVOLVIMENTO", "ALGORITMO", "COMPUTADOR" }; //! Cria a variável palavras que contém um array ("lista") dentro dela
        String palavraEscolhida = palavras[(int) (Math.random() * palavras.length)]; //! Escolhe aleatoriamente a palavra do jogo
        StringBuilder palavraAdivinhada = new StringBuilder("_".repeat(palavraEscolhida.length())); //! Pega o tamanho da palavra escolhida e converte em "_"

        int tentativas = 6; //! Cria variável para receber o número de tentativas
        boolean jogoEmAndamento = true; //! Define se o jogo está rodando ou não 

        char[] letrasUsadas = new char[palavraEscolhida.length()]; //! NEW: Array para armazenar as letras usadas
        int tamanho = 0;

        System.out.println("Bem-vindo ao Jogo da Forca!");

        while (jogoEmAndamento) {
            System.out.println("\nPalavra: " + palavraAdivinhada);
            System.out.println("Você tem " + tentativas + " tentativas restantes.");
            System.out.print("Digite uma letra: ");

            char letra = scanner.next().toUpperCase().charAt(0); //! Cria variável letra para armazenar a PRIMEIRA LETRA que o jogador escreveu e convertendo-a para maiúsculo

            if (letraJaUsada(letrasUsadas, letra, tamanho)) { //! NEW: Verifica se a letra já foi usada
                System.out.println("Você já usou a letra '" + letra + "'. Tente outra.");
                continue;
            }

            letrasUsadas[tamanho] = letra; //! NEW: Armazena a letra usada
            tamanho++;

            if (palavraEscolhida.indexOf(letra) >= 0) { //! Verifica se a letra digitada está na palavra escolhida
                for (int i = 0; i < palavraEscolhida.length(); i++) { //! Procura se a letra digitada está em cada uma das posições da palavra escolhida
                    if (palavraEscolhida.charAt(i) == letra) {
                        palavraAdivinhada.setCharAt(i, letra);
                    }
                }
                System.out.println("Boa! A letra '" + letra + "' está na palavra.");
            } else { //! Se o jogador erra a letra, diminui em 1 o número de tentativas
                tentativas--;
                System.out.println("Ops! A letra '" + letra + "' não está na palavra.");
            }

            if (palavraAdivinhada.toString().equals(palavraEscolhida)) { //! Verifica se o jogador ganhou
                System.out.println("\nParabéns! Você adivinhou a palavra: " + palavraEscolhida);
                jogoEmAndamento = false;
            } else if (tentativas == 0) {
                System.out.println("\nVocê perdeu! A palavra era: " + palavraEscolhida);
                jogoEmAndamento = false;
            }
        }
        
        scanner.close();
    }
    
    private static boolean letraJaUsada(char[] letrasUsadas, char letra, int tamanho) { //! NEW: Método que verifica se a letra já foi usada
        for (int i = 0; i < tamanho; i++) {
            if (letrasUsadas[i] == letra) {
                return true;  //! NEW: A letra já foi usada
            }
        }
        return false;  //! NEW: A letra não foi usada
    }
}