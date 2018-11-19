package leitoresEscritores;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

import leitoresEscritores.estrategias.EstrategiaPermissao;
import leitoresEscritores.estrategias.EstrategiaPermissaoBloqueiaSempre;
import leitoresEscritores.estrategias.EstrategiaPermissaoPrioridadeEscritores;

public class Main {

	// sem uso de Leitores e Escritores, o sistema eh bloqueado em todo e qualquer
	// acesso a base
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

		long tempoFim = System.currentTimeMillis();
		return tempoFim - tempoInicio;
	}

	// prioridade dos leitores, o sistema eh bloqueado so em caso de escritores
	// leitores tem preferencia sob os escritores, deixando eles por ultimo sempre

	
	// permuta as posicoes do arranjo de threads
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

	public static void main(String[] args) throws InterruptedException {
		try {
			BancoDeDados bd = new BancoDeDados("bd.txt");

			long duracaoTotalGeral = 0l;
			for (int nLeitores = 0; nLeitores <= 100; nLeitores++) {
				int nEscritores = 100 - nLeitores;
				long duracaoTotal = 0l;
				// executa o problema 50 vezes
				for (int i = 0; i < 50; i++) {
					duracaoTotal += executaProblema(bd, nLeitores, nEscritores, new EstrategiaPermissaoPrioridadeEscritores());
				}

				System.out.println(nLeitores + ";" + duracaoTotal / 50);
				duracaoTotalGeral += duracaoTotal;
			}
			System.out
					.println("Duração total de todos os casos pra problema: " + duracaoTotalGeral);

		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo");
		}

		System.out.println("fim do programinha fofo");
	}
}
