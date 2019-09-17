package neu.edu.GAprogram;

import java.util.ArrayList;
import java.util.Random;

import static jdk.nashorn.internal.objects.NativeMath.random;

public class test {
    Random random = new Random();
    // Mutation rate
    double mutationRate;
    // Population total
    int totalPopulation = 150;

    // Population array
    DNA[] population;
    // Mating pool ArrayList
    ArrayList<DNA> matingPool;
    // Target phrase
    String target="to be or not to be";

    void setup() {

        // Initializing target phrase and mutation rate

        mutationRate = 0.01;

        // <b><span style="font-size: 110%; text-decoration:underline;">Step 1: Initialize Population</b></span>
        population = new DNA[totalPopulation];
        for (int i = 0; i < population.length; i++) {
            population[i] = new DNA();
        }
    }

    void draw() {

        // <b><span style="font-size: 110%; text-decoration:underline;">Step 2: Selection</b></span>

        // Step 2a: Calculate fitness.
        for (int i = 0; i < population.length; i++) {
            population[i].fitness();
        }

        // Step 2b: Build mating pool.
        ArrayList<DNA> matingPool = new ArrayList<DNA>();

        for (int i = 0; i < population.length; i++) {
            //[full] Add each member n times according to its fitness score.
            int n = (int)population[i].fitness * 100;
            for (int j = 0; j < n; j++) {
                matingPool.add(population[i]);
            }
            //[end]
        }

        // <b><span style="font-size: 110%; text-decoration:underline;">Step 3: Reproduction</b></span>
        for (int i = 0; i < population.length; i++) {
            int a = (int)random(matingPool.size());
            int b = (int)random(matingPool.size());
            DNA partnerA = matingPool.get(a);
            DNA partnerB = matingPool.get(b);
            // Step 3a: Crossover
            DNA child = partnerA.crossover(partnerB);
            // Step 3b: Mutation
            child.mutate(mutationRate);

            // Note that we are overwriting the population with the new
            // children.  When draw() loops, we will perform all the same
            // steps with the new population of children.
            population[i] = child;
        }
    }

    class DNA {


        char[] genes;
        float fitness;

        //[full] Create DNA randomly.
        DNA() {
            genes = new char[target.length()];
            for (int i = 0; i < genes.length; i++) {
                genes[i] = (char) (random.nextInt(97) + 32);
            }
        }
        //[end]

        //[full] Calculate fitness.
        void fitness() {
            int score = 0;
            for (int i = 0; i < genes.length; i++) {
                if (genes[i] == target.charAt(i)) {
                    score++;
                }
            }
            fitness = (float)score/target.length();
        }
        //[end]

        //[full] Crossover
        DNA crossover(DNA partner) {
            DNA child = new DNA();
            int midpoint = random.nextInt(genes.length);
            for (int i = 0; i < genes.length; i++) {
                if (i > midpoint) child.genes[i] = genes[i];
                else              child.genes[i] = partner.genes[i];
            }
            return child;
        }
        //[end]

        //[full] Mutation
        void mutate(double mutationRate) {
            for (int i = 0; i < genes.length; i++) {
                if (random(1) < mutationRate) {
                    genes[i] = (char) (random.nextInt(97) + 32);
                }
            }
        }
        //[end]

        //[full] Convert to String—PHENOTYPE.
        String getPhrase() {
            return new String(genes);
        }
        //[end]

    }

}
