package leitoresEscritores.estrategias;

import java.util.concurrent.Semaphore;

public class EstrategiaPermissaoPrioridadeEscritores implements EstrategiaPermissao {
	// semaforo para controlar o recurso a regiao compartilhada
	Semaphore acessoRecurso = new Semaphore(1);
	// semaforo para controle dos leitores na regiao compartilhada
	Semaphore acessoNLeitores = new Semaphore(1);
	// semaforo para controle da fila de espera 
	Semaphore filaServico = new Semaphore(1);
	// contador de leitores na regiao compartilhada
	int nLeitores = 0;

	// bloqueia a fila, incrementa o contador de leitores, se for o primeiro leitor bloqueia o recurso compartilhado, libera a fila 
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
	
	// decrementa o numero de leitores, libera o recurso caso seja o ultimo leitor saindo
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

	// bloqueia a fila, bloqueia o recurso compartilhado, libera a fila
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

	// libera o recurso
	@Override
	public void liberaPermissaoEscritor() {
		acessoRecurso.release();
	}

}
