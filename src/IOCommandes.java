
import com.sun.source.tree.BreakTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.regex.*;


class IOCommandes{
	
	private BufferedReader lectureEcran;
	private PrintStream ecritureEcran;
	private BufferedReader lectureReseau;
	private PrintStream ecritureReseau;
	private FileInputStream fis;
	private FileWriter fos;
	private Processus [] tabP;
	
	
	IOCommandes(){
		lectureEcran = new BufferedReader(new InputStreamReader(System.in));
		ecritureEcran = System.out;
	}	
	
		
	public void ecrireEcran(String text){
		ecritureEcran.println(text);
	}
	
	public String lireEcran(){
		String line=null;
		try {
			line=lectureEcran.readLine();
		}catch (IOException except){
	          except.printStackTrace();
	        }		
		return line;
	}

	
	//----------------File
	
	//nombre total de processus
	public int numberOccur(String text, String regex) {
    Matcher matcher = Pattern.compile(regex).matcher(text);
    int occur = 0;
    while(matcher.find()) {
        occur ++;
    }
    return occur;
}

	//lecture ligne par ligne du fichier séparer par des \n
	public String lireFile(File f){
		
		String textF="";

		try {	
			fis = new FileInputStream(f);
			BufferedReader lineBuff = new BufferedReader(new InputStreamReader(fis));
			try {
				String line=lineBuff.readLine();
				while (line !=null){
					//ecrireEcran(line);
					textF += line + "\n";
					line=lineBuff.readLine();
				}	
				lineBuff.close();
			}catch (IOException e){
	          e.printStackTrace();
	        }
		}catch (FileNotFoundException e){
	          //e.printStackTrace();
			  ecrireEcran("Fichier intouvable !\n");
	        }
		return textF;		
	}


	//affichage des processus à partir du retour String de lireFile
	public void afficheProcess(String data){
		String[] lines = data.split("\n");

		int NB = lines.length -1;
		int i = 0;
		for(; i < NB; i++)
		{
			ecrireEcran(lines[i+1] + "\n");		
		}	
	}
	

	//création du tableau des processus à partir du retour String de lireFile
	Processus[] tableProcess(String data){
			
		String[] lines = data.split("\n");

		int NB = lines.length -1;
		
		Processus[] process = new Processus[NB];
			
		int i = 0;
		for(; i < NB; i++)
		{
			//io.ecrireEcran(lines[i+1] + "\n");
			String [] splitArray=lines[i+1].split("\t");
		
			process[i]=new Processus(splitArray[0], Float.parseFloat(splitArray[1]), Float.parseFloat(splitArray[2]), Float.parseFloat(splitArray[3]), Float.parseFloat(splitArray[4]), Integer.parseInt(splitArray[5]));

		}
		return process;
		
	}

	//écriture dans un fichier
	public void ecrireFile(String text, String msg){
		try {
			fos = new FileWriter(text, true); //absolument le true sinon écrasement
			BufferedWriter fot=new BufferedWriter(fos);
			
			fot.write(msg+"\n");						
			fot.close();
			
		}catch (IOException e){
	          e.printStackTrace();
	        }		
	}
	
	//Directory
	public String[] lireRepertoire(String text){
		File file=new File(text);
		String [] listeFiles;
		int i = 0;
		if(file.exists()){
			if(file.isDirectory()){
				
				listeFiles=file.list();
				if (listeFiles == null) {
					return null;
				}
				for (; i < listeFiles.length; i++){
					int indice=i+1;
					ecrireEcran(indice + " - " + listeFiles[i] + "\n");
				}
				return listeFiles;
			}
			else{
				ecrireEcran(text + " n'est pas un repertoire");
			}	
		}
		else{
				ecrireEcran(text + " n'existe pas");
			}
		return null;
	}


	private void moveProcess(Processus[] processuses, int begin) {
		if (processuses.length - 1 - begin >= 0)
			System.arraycopy(processuses, begin, processuses, begin + 1, processuses.length - 1 - begin);
	}


	public Processus[] reorgProcessFIFO(Processus[] processuses) {
		Processus[] returnList = new Processus[processuses.length];
		returnList[0] = processuses[0];

		for (int i = 1; i < processuses.length; i++) {
			Processus proc = processuses[i];
			int procId = 0;

			// Vérification de la date d'arrivée
			for (; procId < returnList.length; procId++) {
				if (returnList[procId] == null) {
					returnList[procId] = proc;
				}
				if (returnList[procId].arrive_t > proc.arrive_t) {
					// Echange de place - on décale de 1 tout ceux d'après
					moveProcess(returnList, procId);
					returnList[procId] = proc;
					break;
				} else if (returnList[procId].arrive_t == proc.arrive_t) {
					// Récupération de la priorité
					if (returnList[procId].priority_l > proc.priority_l) {
						moveProcess(returnList, procId);
						returnList[procId] = proc;
						break;
					} else if (returnList[procId].priority_l == proc.priority_l) {
						if (returnList[procId].ioAt_t > proc.ioAt_t) {
							moveProcess(returnList, procId);
							returnList[procId] = proc;
							break;
						}
					}
				}
			}
		}
		return returnList;
	}
}