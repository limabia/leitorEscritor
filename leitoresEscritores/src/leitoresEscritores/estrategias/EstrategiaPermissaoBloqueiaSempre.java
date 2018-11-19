package leitoresEscritores.estrategias;

import java.util.concurrent.Semaphore;

public class EstrategiaPermissaoBloqueiaSempre implements EstrategiaPermissao {

	Semaphore mutex = new Semaphore(1);

	@Override
	public void obtemPermissaoLeitor() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void obtemPermissaoEscritor() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void liberaPermissaoLeitor() {
		mutex.release();
	}

	@Override
	public void liberaPermissaoEscritor() {
		mutex.release();
	}

}
