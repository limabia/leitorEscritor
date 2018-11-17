package leitoresEscritores;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Escritor extends Thread {

	private BancoDeDados bd;
	private Semaphore mutex;

	public Escritor(BancoDeDados bd, Semaphore mutex) {
		super();
		this.bd = bd;
		this.mutex = mutex;
	}

	@Override
	public void run() {
		try {
			mutex.acquire();

			ThreadLocalRandom generator = ThreadLocalRandom.current();
			for (int i = 0; i < 100; i++) {
				int posAleatoria = generator.nextInt(0, bd.tamanho());
				bd.escreverPosicao(posAleatoria, "MODIFICADO");
			}
			sleep();

			mutex.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sleep() {
		try {
			sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}