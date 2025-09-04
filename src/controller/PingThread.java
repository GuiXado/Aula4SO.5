package controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PingThread extends Thread {
	
	private String servidor;
	private String nomeServidor;
	
	//pega os sites a serem pingados
	public PingThread(String nomeServidor, String servidor) {
		this.nomeServidor = nomeServidor;
		this.servidor = servidor;
	}
	
	public void run() {
		// verifica SO // vai acabar mostrado 3 vezes
		String os = System.getProperty("os.name");
		if (!os.contains("Linux")) {
			System.out.println("Sistema Operacional não suportado: " + os);
			return;
		}
	
		String comando = "ping -4 -c 10 " + servidor;

		try {
			Process p = Runtime.getRuntime().exec(comando);
			InputStream fluxo = p.getInputStream();
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);

			String linha;
			double soma = 0;
			int contador = 0;
			
			//String linha = buffer.readLine();
			
			while ((linha = buffer.readLine()) != null) {
				//System.out.println(linha);
				// esse avg já é a média
				if (linha.contains("rtt min/avg/max")) {
					String[] partes = linha.split("=");
					if (partes.length > 1) {
						String[] valores = partes[1].trim().split("/");
						String media = valores[1]; // posição 1 = avg
						System.out.println(nomeServidor + " -> Tempo médio (rtt): " + media + " ms");
					}
				}
			}

			if (contador > 0) {
				double media = soma / contador;
				System.out.println(nomeServidor + " -> Tempo médio (calculado): " + String.format("%.2f", media) + " ms");
			}
				
			buffer.close();
			leitor.close();
			fluxo.close();
		// try
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    }
}
