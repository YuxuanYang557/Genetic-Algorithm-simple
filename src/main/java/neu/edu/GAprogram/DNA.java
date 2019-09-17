package neu.edu.GAprogram;

import java.util.Random;

public class DNA{

    Random random = new Random();

    String target;
    //each DNA, each individual in the population has its fitness toward the target
    float fitness;
    //the array of char is to represent the unique DNA sequence
    char[] genes;

    //the constructor --- generate the random information
    public DNA(String goal) {

        target = goal;
        int len = target.length();
        genes = new char[len];

        for(int i = 0; i< genes.length; i++) {
            //we want to chose the character between 32-128 to represent letters in ASCII
            //use the formula new Random().nextInt((max-min)-1)+min
            genes[i] = (char) (random.nextInt(97) + 32);
        }
    }

    //the method which calculates the fitness for specific DNA in population
    void fitness() {
        int score = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == target.charAt(i)) {
                score++;
            }
        }
        fitness = score/target.length();
    }

    //the method used in reproduction process
    //we chose the point split the genes into two part, one from parent one, another from parent two
    public DNA crossover(DNA partern) {
        DNA child = new DNA(target);
        int midpoint = random.nextInt(genes.length);
        for(int i = 0; i< genes.length; i++) {
            if(i < midpoint) child.genes[i] = this.genes[i];
            else child.genes[i] = partern.genes[i];
        }
        return child;
    }

    //generate a random double number between 0 and 1, and compare to mutation rate
    public void mutation(double mutationRate) {
        for(int i = 0; i< this.genes.length; i++) {
            if(Math.random() < mutationRate) {
                this.genes[i] = (char) (random.nextInt(97) + 32);
            }
        }
    }
}
