package leitoresEscritores;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

import leitoresEscritores.estrategias.EstrategiaPermissao;
import leitoresEscritores.estrategias.EstrategiaPermissaoBloqueiaSempre;
import leitoresEscritores.estrategias.EstrategiaPermissaoPrioridadeLeitores;
import leitoresEscritores.estrategias.EstrategiaPermissaoPrioridadeEscritores;

public class Main {
	// classe responsavel por executar o problema de fator, de acordo com a estrategia escolhida
	public static long executaProblema(BancoDeDados bd, int leitores, int escritores, EstrategiaPermissao estrategiaPermissao) {
		long tempoInicio = System.currentTimeMillis();
		Thread[] threads = new Thread[leitores + escritores];

		// Cria leitores
		for (int i = 0; i < leitores; i++) {
			threads[i] = new Leitor(bd, estrategiaPermissao);
		}
		// Cria escritores
		for (int i = 0; i < escritores; i++) {
			threads[leitores + i] = new Escritor(bd, estrategiaPermissao);
		}

		// Embaralha as threads
		embaralhaArray(threads);

		// Executa os leitores/escritores
		for (int i = 0; i < threads.length; i++)
			threads[i].start();

		// Join para aguardar o fim da execução de todas threads
		try {
			for (int i = 0; i < threads.length; i++)
				threads[i].join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// controle do tempo de execucao
		long tempoFim = System.currentTimeMillis();
		return tempoFim - tempoInicio;
	}
	
	// responsavel por permutar as posicoes do arranjo de threads
	private static void embaralhaArray(Object[] array) {
		ThreadLocalRandom generator = ThreadLocalRandom.current();
		for (int i = 0; i < array.length - 1; i++) {
			int random = generator.nextInt(i, array.length);
			// troca i com random
			Object aux = array[i];
			array[i] = array[random];
			array[random] = aux;
		}
	}

	private static void executaEstrategia(EstrategiaPermissao estrategia) {
		try {
			BancoDeDados bd = new BancoDeDados("bd.txt");
			System.out.println("numero de leitores ; tempo de execução medio");
			// controla a proporcao de leitores e escritores
			for (int nLeitores = 0; nLeitores <= 100; nLeitores++) {
				int nEscritores = 100 - nLeitores;
				long duracaoTotal = 0l;
				// executa o problema 50 vezes 
				for (int i = 0; i < 50; i++) {
					duracaoTotal += executaProblema(bd, nLeitores, nEscritores, estrategia);
				}
				System.out.println(nLeitores + ";" + duracaoTotal / 50);	
			}
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo de dados");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		// determina qual estrategia sera executada de acordo com a escolha do usuario
		if (args.length == 1){
			
			// estrategia default
			EstrategiaPermissao estrategia = new EstrategiaPermissaoBloqueiaSempre();
	
			String estrategiaEscolhida = args[0];
			
			if(estrategiaEscolhida == "1") {
				System.out.println("A estratégia Bloqueia Sempre será executada");
			}
			else if (estrategiaEscolhida == "2") {
				estrategia = new EstrategiaPermissaoPrioridadeLeitores();
				System.out.println("A estratégia prioridade leitores será executada");
			}
			else if (estrategiaEscolhida == "3") {
				estrategia = new EstrategiaPermissaoPrioridadeEscritores();
				System.out.println("A estratégia prioridade escritores será executada");
			}
			
			executaEstrategia(estrategia);
		}
		else {
			System.out.println("escolha uma estrategia para ser executada");
		}

		System.out.println("fim do programa");
	}
}
