package leitoresEscritores;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

	public static long executaProblemaBloqueandoSempre(BancoDeDados bd, int leitores, int escritores) {
		long tempoInicio = System.currentTimeMillis();

		Thread[] threads = new Thread[leitores + escritores];
		Semaphore mutex = new Semaphore(1);
		
		// Cria leitores
		for (int i = 0; i < leitores; i++) {
			threads[i] = new Leitor(bd, mutex);
		}
		// Cria escritores
		for (int i = 0; i < escritores; i++) {
			threads[leitores + i] = new Escritor(bd, mutex);
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

			for (int nLeitores = 0; nLeitores <= 100; nLeitores++) {
				int nEscritores = 100 - nLeitores;
				long duracaoTotal = 0l;
				// executa o problema 50 vezes
				for (int i = 0; i < 50; i++) {
					duracaoTotal += executaProblemaBloqueandoSempre(bd, nLeitores, nEscritores);
				}

				System.out.println("Duração média de execução: " + duracaoTotal / 50 + "ms");
			}
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo");
		}
		System.out.println("fim do programinha fofo");
	}
}