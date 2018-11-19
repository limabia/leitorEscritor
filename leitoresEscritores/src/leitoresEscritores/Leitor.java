package leitoresEscritores;

import java.util.concurrent.ThreadLocalRandom;

import leitoresEscritores.estrategias.EstrategiaPermissao;

public class Leitor extends Thread {
	private BancoDeDados bd;
	private EstrategiaPermissao estrategiaPermissao;

	public Leitor(BancoDeDados bd, EstrategiaPermissao estrategiaPermissao) {
		super();
		this.bd = bd;
		this.estrategiaPermissao = estrategiaPermissao;
	}

	@Override
	@SuppressWarnings("unused")
	public void run() {
		estrategiaPermissao.obtemPermissaoLeitor();
		
		ThreadLocalRandom generator = ThreadLocalRandom.current();
		for (int i = 0; i < 100; i++) {
			int posAleatoria = generator.nextInt(0, bd.tamanho());
			String linhaLida = bd.lerPosicao(posAleatoria);
		}
		sleep();
		
		estrategiaPermissao.liberaPermissaoLeitor();
	}

	private void sleep() {
		try {
			sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
