package leitoresEscritores.estrategias;

public interface EstrategiaPermissao {
	public void obtemPermissaoLeitor();
	public void obtemPermissaoEscritor();
	
	public void liberaPermissaoLeitor();
	public void liberaPermissaoEscritor();
}
