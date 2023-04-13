import java.io.File;
import java.util.Scanner;

class Dispatcher{

	public static void main(String[] args){
		IOCommandes ecran = new IOCommandes();

		// Selection du fichier
		String[] fileList = ecran.lireRepertoire("files");
		Scanner input = new Scanner(System.in);
		int numero = -1;
		boolean first = true;
		ecran.ecrireEcran("Sélectionnez le fichier de processus à charger : ");
		while (numero < 1 || numero >= fileList.length) {
			if (!first) {
				ecran.ecrireEcran("Le numéro entré n'est pas valide");
			}
			numero = input.nextInt();
			first = false;
		}

		// Chargement du fichier
		String fileName = fileList[numero-1];
		String fileText = ecran.lireFile(new File("files\\" + fileName));

		// Traitement en FIFO
		Processus[] processuses = ecran.tableProcess(fileText);
		ecran.ecrireEcran("Traitement des processus via FIFO...");

		// Réorganisation de la liste selon les critères
		Processus[] orderedProc = ecran.reorgProcessFIFO(processuses);
		ecran.ecrireEcran("");
		
		// Execution des processus dans l'ordre FIFO
		// TODO: 13/04/2023  
	}

}
