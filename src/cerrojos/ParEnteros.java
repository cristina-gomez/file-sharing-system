package cerrojos;

public class ParEnteros {
	
	private int num1;
	private int num2;
	
	public ParEnteros(int comp1, int comp2) {
		num1=comp1;
		num2=comp2;
	}
	
	public int getPrimero() {
		return num1;
	}
	
	public int getSegundo() {
		return num2;
	}
	
	public Boolean mayorQue(ParEnteros par2){
		if(num1>par2.getPrimero()||(num1==par2.getPrimero()&&num2>par2.getSegundo())) return true;
		else return false;
	}

}
