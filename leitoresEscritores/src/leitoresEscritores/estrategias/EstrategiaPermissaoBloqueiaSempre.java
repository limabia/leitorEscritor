package leitoresEscritores.estrategias;

import java.util.concurrent.Semaphore;

public class EstrategiaPermissaoBloqueiaSempre implements EstrategiaPermissao {

	// semaforo para controle da regiao compartilhada
	Semaphore mutex = new Semaphore(1);

	/* tanto leitor quanto escritores tem acesso unico a regiao compartilhada, ou seja,
	 * basta ter um acessando que para que a regiao fique bloqueada */

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
