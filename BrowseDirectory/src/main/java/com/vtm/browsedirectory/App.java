package com.vtm.browsedirectory;
import com.vtm.browsedirectory.Process.Process;

public final class App {
    public static void main(String[] args) {
        // ArrayList<SearchHis> arr = new ArrayList<>();
        // arr.add(new SearchHis(new Date(), "Slag"));
        // arr.add(new SearchHis(new Date(), "Slag"));
        // arr.add(new SearchHis(new Date(), "Slag"));
        // try {
        //     ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./src/main/res/data/history.dat"));
        //     objectOutputStream.writeObject(arr);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        Process process = new Process();
        process.run();
    }
}
