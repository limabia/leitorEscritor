package leitoresEscritores;

import java.util.concurrent.ThreadLocalRandom;

import leitoresEscritores.estrategias.EstrategiaPermissao;

public class Escritor extends Thread {

	private BancoDeDados bd;
	private EstrategiaPermissao estrategiaPermissao;

	public Escritor(BancoDeDados bd, EstrategiaPermissao estrategiaPermissao) {
		super();
		this.bd = bd;
		this.estrategiaPermissao = estrategiaPermissao;
	}

	@Override
	public void run() {
		estrategiaPermissao.obtemPermissaoEscritor();

		ThreadLocalRandom generator = ThreadLocalRandom.current();
		for (int i = 0; i < 100; i++) {
			int posAleatoria = generator.nextInt(0, bd.tamanho());
			bd.escreverPosicao(posAleatoria, "MODIFICADO");
		}
		sleep();

		estrategiaPermissao.liberaPermissaoEscritor();
	}

	private void sleep() {
		try {
			sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
