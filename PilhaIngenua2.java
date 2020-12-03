import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PilhaIngenua2 {
	
	protected int capacity;
	public static final int MAX = 8;
	protected Integer [] pilha;
	protected int size;
	
	PilhaIngenua2() {
		pilha = new Integer[MAX];
	}
	
	public static BufferedReader leitor(String path) {
		try {
			FileInputStream fstream = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			return br;
		} catch (Exception e) {
			System.out.println("Erro ao ler arquivo " + path + " : " + e.getMessage());
			return null;
		}
	}
	
	public static BufferedWriter escritor(String path) {
		try {
			FileOutputStream fstream = new FileOutputStream(path);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fstream));
			return bw;
		} catch (Exception e) {
			System.out.println("Erro ao escrever no arquivo " + path + " : " + e.getMessage());
			return null;
		}
	}
	
	public void add(int newElement)  {
		if(size == capacity){
		capacity += MAX;
		Integer[] tmp = new Integer [capacity] ;
		for (int j = 0 ; j < size ; j++){
		tmp [j] = pilha [j];
		pilha = tmp ;
										}
		}
		pilha[size] = newElement ;
		size++;
	}	
	public int remove() {
		if (pilha[size] != null){
		int tmp = pilha[size] ;
		size--;
		return tmp;
	}
		return 0;
	}
	
	public void limparPilha() {
		int i;
		for (i=0; i < pilha.length; i++) pilha[i] = null;
	}
	
	public static void main(String[] args) {
		try {
			String dirArqLog = "C:\\Users\\guine\\OneDrive\\Área de Trabalho\\EP3\\log.txt";			// Insira aqui o caminho do arquivo que recebera o log
			String dirArqTempo = "C:\\Users\\guine\\OneDrive\\Área de Trabalho\\EP3\\tempo.csv";		// Insira aqui o caminho do arquivo que recebera o tempo de processamento
			String dirPastaEntradas = "C:\\Users\\guine\\OneDrive\\Área de Trabalho\\EP3\\Entradas";	// Insira aqui o caminho da pasta que contem os arquivos de entrada

//            String dirArqLog = "/Users/stacks-efficiency/saidas/log_PilhaIngenua.txt";
//            String dirArqTempo = "/Users/stacks-efficiency/saidas/tempo_PilhaIngenua.csv";
//            String dirPastaEntradas = "/Users/stacks-efficiency/entradas";

			BufferedWriter log = escritor(dirArqLog);
			BufferedWriter tempo = escritor(dirArqTempo);
			tempo.write("Nome do Arquivo;Tempo de Processamento(ms);Quantidade de Linhas"); // Cabecalho do arquivo dos tempos de processamento
			tempo.newLine();
			
			File directoryPath = new File(dirPastaEntradas);
			String contents[] = directoryPath.list(); // Lista com todos os arquivos existentes na pasta de entrada
			
			PilhaIngenua2 pilhaIngenua = new PilhaIngenua2();
			BufferedReader leitor;
			String str;
			
			for (int i = 0; i < contents.length; i++) { // Percorre os arquivos da pasta de entrada
				System.out.println("Processando Arquivo: " + contents[i]);
				log.write("Processando Arquivo: " + contents[i]);
				log.newLine();
				log.newLine();
				
				leitor = leitor(dirPastaEntradas + "\\" + contents[i]);
//                leitor = leitor(dirPastaEntradas + "/" + contents[i]);

				str = leitor.readLine();
				int qntLinhas = 0;
				long t = System.currentTimeMillis();
				
				while (str != null) { // Percorre as linhas do arquivo
					qntLinhas++;
					
					if (str.trim().equals("")) {
						// log.write(String.valueOf(pilhaIngenua.remove()));
						log.newLine();
					} else {
						pilhaIngenua.add(Integer.parseInt(str));
					}
					str = leitor.readLine();
				}
				t = System.currentTimeMillis() - t;
				
				tempo.write(contents[i] + ";" + t + ";" + qntLinhas);
				tempo.newLine();
				if (i != contents.length - 1) log.newLine();
				pilhaIngenua.limparPilha();
				leitor.close();
			}
			log.close();
			tempo.close();
			
		} catch (Exception e) {
			System.out.println("Erro ao executar o programa (Possivelmente Arquivo de Entrada Corrompido): " + e.getMessage());
			e.printStackTrace();
		}
	}
}
