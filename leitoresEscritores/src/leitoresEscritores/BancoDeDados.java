package leitoresEscritores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BancoDeDados {
	private ArrayList<String> dados;

	public BancoDeDados(String arquivo) throws IOException {
		dados = new ArrayList<String>();

		FileReader fileReader = new FileReader(arquivo);
		BufferedReader lerArq = new BufferedReader(fileReader);
		String linha = lerArq.readLine();

		while (linha != null) {
			linha = lerArq.readLine();
			dados.add(linha);
		}
		fileReader.close();
	}

	public int tamanho() {
		return this.dados.size();
	}

	public String lerPosicao(int posicao) {
		return this.dados.get(posicao);
	}

	public void escreverPosicao(int posicao, String conteudo) {
		this.dados.set(posicao, conteudo);
	}

}