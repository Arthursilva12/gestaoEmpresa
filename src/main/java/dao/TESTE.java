package dao;

public class TESTE {

	public static void main(String[] args) {

		Double qtdRegistro = 31.0;
		Double paginas = 5.0;
		
		Double pagina = qtdRegistro / paginas;
		Double resto = pagina % 2;
		
		if(resto > 0) {
			pagina++;
		}
		
		System.out.println(pagina.intValue());
		
	}

}
