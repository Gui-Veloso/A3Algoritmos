//importações
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//classe cliente
class Cliente {
    int horarioChegada;

    //construtor
    Cliente(int horarioChegada) {
        this.horarioChegada = horarioChegada;
    }
}

//classe guiche
class Guiche {
    //atributos
    boolean ocupado;
    int tempoOcupado;

    //construtor
    Guiche() {
        this.ocupado = false;
        this.tempoOcupado = 0;
    }
}

//Simulação
public class SimulacaoAgenciaBancaria {
    //método que verifica se um cliente chega
    public static boolean clienteChega() {
        //sempre que o número aleatório for 0, equivale a chegada de um cliente
        return new Random().nextInt(30) == 0;
    }

    //método que simula uma ação do cliente
    public static String[] iniciaTransacao() {
        Random random = new Random();
        int transacao = random.nextInt(3);
        switch (transacao) {
            case 0:
                return new String[]{"saque", "60"};
            case 1:
                return new String[]{"deposito", "90"};
            default:
                return new String[]{"pagamento", "120"};
        }
    }

    //método que cria nosso sistema de filas e simula o expediente
    public static void simulaExpediente() {
        //cria nossa lista de clientes
        List<Cliente> fila = new ArrayList<>();
        //cria um array de 3 guichês
        Guiche[] guiches = new Guiche[3];
        for (int i = 0; i < 3; i++) {
            guiches[i] = new Guiche();
        }

        // variáveis contadoras/acumuladoras, vão ajudar a quantificar
        int clientesAtendidos = 0;
        int clientesSaque = 0;
        int clientesDeposito = 0;
        int clientesPagamento = 0;
        int tempoTotalEspera = 0;

        // loop para simular 6 horas de expediente (21600 segundos)
        for (int tempoAtual = 0; tempoAtual < 21600; tempoAtual++) {
            // se um cliente chega, adiciona ele à fila
            if (clienteChega()) {
                fila.add(new Cliente(tempoAtual));
                //System.out.println("horario de chegada do cliente: " + tempoAtual);
            }

            // verifica cada guichê
            for (Guiche guiche : guiches) {
                // se o guichê não está ocupado e há clientes na fila
                if (!guiche.ocupado && !fila.isEmpty()) {
                    // remove o primeiro cliente da fila
                    Cliente cliente = fila.remove(0);
                    // criamos um array de string para armazenar o tipo de transação do cliente
                    String[] transacao = iniciaTransacao();
                    // marca o guichê como ocupado e define o tempo da transação
                    guiche.ocupado = true;
                    guiche.tempoOcupado = Integer.parseInt(transacao[1]);
                    // incrementa o contador de clientes atendidos
                    clientesAtendidos++;

                    // neste trecho, nós utilizamos o switch case para separar os acumuladores/contadores e incrementar individualmente
                    switch (transacao[0]) {
                        case "saque":
                            clientesSaque++;
                            break;
                        case "deposito":
                            clientesDeposito++;
                            break;
                        case "pagamento":
                            clientesPagamento++;
                            break;
                    }

                    // calcula o tempo de espera do cliente
                    int tempoEspera = tempoAtual - cliente.horarioChegada;
                    // acumula o tempo total de espera
                    tempoTotalEspera += tempoEspera;
                }
            }

            // decrementa o tempo ocupado dos guichês ocupados
            for (Guiche guiche : guiches) {
                if (guiche.ocupado) {
                    guiche.tempoOcupado--;
                    // se o tempo ocupado chega a zero, libera o guichê
                    if (guiche.tempoOcupado == 0) {
                        guiche.ocupado = false;
                    }
                }
            }
        } // fim do loop expediente
        
        // calcula o tempo extra de expediente, utilizamos o valor fixo de 90 (média de tempo gasto durante as operações), 
        float tempoExtra = fila.size() * 90;
        // calcula o tempo médio de espera na fila, convertemos tempo de espera para double, pois foi declarado como int
        double  tempoMedioEspera = (double) tempoTotalEspera / clientesAtendidos;
       

        // imprime os resultados
        System.out.println("** SIMULACAO DA AGENCIA BANCARIA DSANIMA **\n\n");
        System.out.println("Durante o expediente, tivemos os seguintes resultados: ");
        System.out.println("Atendemos o total de: " + clientesAtendidos + " clientes !");
        System.out.println("A quantidade de clientes que fizeram saque foi de: " + clientesSaque);
        System.out.println("A quantidade de clientes que fizeram deposito foi de: " + clientesDeposito);
        System.out.println("A quantidade de clientes que fizeram pagamento foi de: " + clientesPagamento + "\n");
        //utilizei o printf, por ser mais fácil de manipular os decimais
        System.out.printf("Durante o expediente, o tempo medio de espera na fila em segundos foi de: %.2f %n", (tempoMedioEspera));
        System.out.println("A nossa estimativa de tempo extra de expediente foi de: " + tempoExtra + " segundos");
        System.out.println("A estimativa em minutos é igual a: " + (tempoExtra/60));
    }

    // método principal que inicia a simulação
    public static void main(String[] args) {
        simulaExpediente();
    }
}
