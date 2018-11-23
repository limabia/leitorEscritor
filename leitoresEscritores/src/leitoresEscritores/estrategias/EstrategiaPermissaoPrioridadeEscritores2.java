package leitoresEscritores.estrategias;

import java.util.concurrent.Semaphore;

public class EstrategiaPermissaoPrioridadeEscritores2 implements EstrategiaPermissao {

	Semaphore acessoRecurso = new Semaphore(1);
	Semaphore acessoNLeitores = new Semaphore(1);
	Semaphore filaServico = new Semaphore(1);
	
	int nLeitores = 0;

	@Override
	public void obtemPermissaoLeitor() {
		try {
			filaServico.acquire();
			acessoNLeitores.acquire();
			nLeitores++;
			if (nLeitores == 1)
				acessoRecurso.acquire();

			acessoNLeitores.release();
			filaServico.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void liberaPermissaoLeitor() {
		try {
			acessoNLeitores.acquire();
			nLeitores--;
			if (nLeitores == 0)
				acessoRecurso.release();
			acessoNLeitores.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void obtemPermissaoEscritor() {
		try {
			filaServico.acquire();
			acessoRecurso.acquire();
			filaServico.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void liberaPermissaoEscritor() {
		// Libero acesso ao recurso
		acessoRecurso.release();
	}

}
