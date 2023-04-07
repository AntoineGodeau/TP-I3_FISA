//Nomprocessus	DateArrive	DureeExecution	DateDebutIO	DureeIO	Priorite

class Processus{
	
		String nameProc, stateProcString; //Process name
		float arrive_t; //Process arrive in the system at
		float remain_t; //remain execution time
		float total_t;
		float ioAt_t; //I/O cycle execution at
		float ioLastF_t; //I/O last for
		int priority_l; // Process priority level
		boolean finished, actif, arrived;
		//Vous pouvez rajouter des variables supplémentaires si besoin
		
		Processus(String name, float ar, float rt, float iot, float iolast, int prio){
			nameProc=name;
			arrive_t=ar;
			remain_t=rt;
			total_t = rt;
			ioAt_t=iot;
			ioLastF_t=iolast;
			priority_l=prio;
			finished = false;
			arrived = false;
			actif = false;
			stateProcString = " ";
		}	
	
	
}