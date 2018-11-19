package leitoresEscritores.estrategias;

import java.util.concurrent.Semaphore;

public class EstrategiaPermissaoPrioridadeLeitores implements EstrategiaPermissao {

	Semaphore mutexRecurso = new Semaphore(1);
	Semaphore mutexLeitores = new Semaphore(1);
	int nLeitores = 0;

	@Override
	public void obtemPermissaoLeitor() {
		try {
			mutexLeitores.acquire();
			nLeitores++;
			if (nLeitores == 1)
				mutexRecurso.acquire();

			mutexLeitores.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void liberaPermissaoLeitor() {
		try {
			mutexLeitores.acquire();
			nLeitores--;
			if (nLeitores == 0)
				mutexRecurso.release();
			mutexLeitores.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void obtemPermissaoEscritor() {
		try {
			mutexRecurso.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void liberaPermissaoEscritor() {
		mutexRecurso.release();
	}

}
