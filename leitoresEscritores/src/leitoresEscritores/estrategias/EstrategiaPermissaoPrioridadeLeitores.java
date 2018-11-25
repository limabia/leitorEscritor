package leitoresEscritores.estrategias;

import java.util.concurrent.Semaphore;

public class EstrategiaPermissaoPrioridadeLeitores implements EstrategiaPermissao {

	// semaforo para controlar o recurso a regiao compartilhada
	Semaphore mutexRecurso = new Semaphore(1);
	// semaforo para controlar o numero de leitores
	Semaphore mutexLeitores = new Semaphore(1);
	// contador de leitores acessando a regiao compartilhada
	int nLeitores = 0;

	// incrementa o contador de leitores e bloqueia o recurso se ele for o primeiro leitor a entrar
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

	// decrementa o contador de leitores e libera o recurso usado pelo leitor se ele for o ultimo leitor a sair
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

	// bloqueia o recurso para o escritor
	@Override
	public void obtemPermissaoEscritor() {
		try {
			mutexRecurso.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// libera o recurso compartilhado
	@Override
	public void liberaPermissaoEscritor() {
		mutexRecurso.release();
	}

}
