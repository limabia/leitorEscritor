package leitoresEscritores.estrategias;

import java.util.concurrent.Semaphore;

public class EstrategiaPermissaoPrioridadeEscritores implements EstrategiaPermissao {

	Semaphore mutexRecurso = new Semaphore(1);
	Semaphore mutexLeitores = new Semaphore(1);
	Semaphore mutexEscritores = new Semaphore(1);
	Semaphore permissaoAdicionarNovoLeitor = new Semaphore(1); // Indica que pode add novos leitores
	
	int nLeitores = 0;
	int nEscritores = 0;

	@Override
	public void obtemPermissaoLeitor() {
		try {
			permissaoAdicionarNovoLeitor.acquire();
			mutexLeitores.acquire();
			nLeitores++;
			if (nLeitores == 1)
				mutexRecurso.acquire();

			mutexLeitores.release();
			permissaoAdicionarNovoLeitor.release();
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
			mutexEscritores.acquire();
			nEscritores++;
			if (nEscritores == 1)
				// Bloqueia a entrada de novos leitores no recurso compartilhado
				permissaoAdicionarNovoLeitor.acquire();
			mutexEscritores.release();
			
			// Obtenho acesso ao recurso
			mutexRecurso.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void liberaPermissaoEscritor() {
		// Libero acesso ao recurso
		mutexRecurso.release();
		
		try {
			mutexEscritores.acquire();
			nEscritores--;
			if (nEscritores == 0)
				// Libero a entrada de novos leitores no recurso compartilhado
				permissaoAdicionarNovoLeitor.release();
			mutexEscritores.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
