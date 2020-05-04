import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Vector;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
    
public class thomas_p1{
    public static void main(String[] args) throws FileNotFoundException, IOException{
	final String NUM = "[0-9]";

	Vector<Integer>[][] array = new Vector[1001][36];
	for(int i = 0; i < array.length; i++){
	    for (int j = 0; j < array[i].length; j++) array[i][j] = new Vector<Integer>();
	}
	
	Boolean[] accepts = new Boolean[1001];
	for (int i = 0; i < accepts.length; i++) accepts[i] = false;

	File f = new File(args[0]);
	FileReader fr = new FileReader(f);
	BufferedReader b = new BufferedReader(fr);

        String nextLine = "";
	int first = -1;
	
	while ((nextLine = b.readLine()) != null){
	    String[] line = nextLine.split("\t");
	    if (line[0].equals("state")){
		if (line[2].equals("start") || (line.length == 4 && line[3].equals("start"))) {
		    first = Integer.parseInt(line[1]);
		}
		if (line[2].equals("accept") || (line.length == 4 && line[3].equals("accept"))) {
		    accepts[Integer.parseInt(line[1])] = true;
		}
	    }
	    else if (line[0].equals("transition")){
		int state = Integer.parseInt(line[1]);
		int transition = 0;
		if (line[2].matches(NUM)) transition = Integer.parseInt(line[2]);
		else transition = line[2].charAt(0) - 87;
		int newState = Integer.parseInt(line[3]);
		array[state][transition].add(newState);
	    }
	}
	b.close();
	
	String[] input = args[1].split("");
	Queue<Integer> states = new LinkedList<>();
	states.add(first);
	for (int i = 0; i < input.length; i++){
	    int tempInt = 0;
	    if (input[i].matches(NUM)) tempInt = Integer.parseInt(input[i]);
	    else tempInt = input[i].charAt(0) - 87;
	    
	    Queue<Integer> temp = new LinkedList<>();
	    while (!states.isEmpty()){
		Vector<Integer> trans = array[states.remove()][tempInt];
		for (int j = 0; j < trans.size(); j++) temp.add(trans.get(j));
	    }
	    states = temp;
	}
	Set<Integer> removedStates = new HashSet<Integer>();
	while(!states.isEmpty()){
	    removedStates.add(states.remove());
	}
	
	Object[] check = removedStates.toArray();
	Vector<Integer> accepted = new Vector<Integer>();
	Vector<Integer> rejected = new Vector<Integer>();

	for (int i = 0; i < check.length; i++){
	    if (accepts[(int)check[i]]) accepted.add((int)check[i]);
	    else rejected.add((int)check[i]);
	}

	String acc = "accept";
	String rej = "reject";
	
	if (!accepted.isEmpty()){
	    for (int i = 0; i < accepted.size(); i++){
		acc += " " + accepted.get(i);
	    }
	    System.out.println(acc);
	}
	else if (accepted.isEmpty()){
	    for (int i = 0; i < rejected.size(); i++){
		rej += " " + rejected.get(i);
	    }
	    System.out.println(rej);
	}
    }
}