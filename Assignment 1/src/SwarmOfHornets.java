public class SwarmOfHornets {
    // A group of hornets
    private Hornet[] hornets;
    private int size;

    public SwarmOfHornets() {
        this.hornets = new Hornet[1];
        this.size = 0;
    }

    public int sizeOfSwarm() {
        // A method which returns the number of hornets that are part of this swarm
        return this.size;
    }

    public Hornet[] getHornets() {
        // A method which returns a group of hornet
        return hornets;
    }

    public Hornet getFirstHornet() {
        // A method which returns the first hornet in the swarm
        return hornets[0];
    }

    public void addHornet(Hornet newHornet) {
        // The method adds the hornet at the end of the queue of hornets in this swarm
        if (hornets.length == size) {
            Hornet[] tmp = new Hornet[hornets.length+1];    // Created a temp array, resize this array
            for (int i=0; i < size; i++) {
                tmp[i] = this.hornets[i];   // copy elements from hornets to tmp
            }
            this.hornets = tmp;    // Assigned back
        }
        this.hornets[size] = newHornet;    // Add the newHornet at the end
        size++;
    }

    public boolean removeHornet(Hornet hornet) {
        // Takes as input a Hornet and returns a boolean. The method removes the first occurrence
        for (int r=0; r<this.hornets.length; r++) {
            if (this.hornets[r] == hornet) {
                Hornet[] tmp = new Hornet[hornets.length-1];    // Created a temp array, resize this array

                for (int i=0, j=0; i<this.hornets.length && j<tmp.length; i++,j++) { // Two variable for loop
                    if (i == r) {
                        i++;
                    }
                    tmp[j] = this.hornets[i];
                }
                this.hornets = tmp;    // Assigned back
                size--;
                if (size==0) this.hornets = new Hornet[1];  // Reinitialize a new hornet array when size equals 0
                return true;
            }
        }
        return false;
    }
}

