package com.vtm.browsedirectory.management;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import com.vtm.browsedirectory.model.SearchHis;

public class SlangMap {
    private HashMap<String, String> sMap;
    private ArrayList<SearchHis> arrHis;
    private static SlangMap instance;

    private SlangMap() {
        this.sMap = new HashMap<>();
        this.arrHis = new ArrayList<>();
    }

    public void loadData(String slangFileName, String hisFileName) {
        ObjectInputStream oInputStream;
        try {
            oInputStream = new ObjectInputStream(new FileInputStream(hisFileName));
            arrHis = (ArrayList<SearchHis>)oInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        try {
            BufferedReader fReader = new BufferedReader(new FileReader(slangFileName));
            String s;
            String slang, definition;
            int pos;
            while ((s = fReader.readLine()) != null) {
                if (s.indexOf("`") != -1) {
                    pos = s.indexOf("`");
                    slang = s.substring(0, pos);
                    definition = s.substring(pos + 1);
                    this.sMap.put(slang, definition);
                }
            }
            fReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static SlangMap getInstance() {
        if (SlangMap.instance == null) {
            SlangMap.instance = new SlangMap();
        }
        return SlangMap.instance;
    }

    public String SlaDefi(String slang, String definition) {
        return slang + "-" + definition;
    }

    public String searchBySlangWord(String slang) {
        String res = "";
        if (this.sMap.containsKey(slang)) {
            res = this.SlaDefi(slang, this.sMap.get(slang));
            arrHis.add(new SearchHis(new Date(), slang));
        }
        return res;
    }

    public ArrayList<String> searchByDefinition(String subDefi) {
        ArrayList<String> res = new ArrayList<>();
        String definition;
        for (String key : this.sMap.keySet()) {
            definition = this.sMap.get(key);
            if (definition.indexOf(subDefi) != -1) {
                res.add(this.SlaDefi(key, definition));
            }
        }
        return res;
    }

    public void printHistory() {
        for (int i = 0; i < this.arrHis.size(); i++) {
            System.out.println(this.arrHis.get(i));
        }
    }

    public Integer addNewSlang(String slang, String defi, BufferedReader bReader) {
        if (this.sMap.containsKey(slang)) {
            String slang1;
            for (Integer i = 1; i < 10; i++) {
                slang1 = slang + "_(" + i.toString() + ")";
                if (this.sMap.containsKey(slang1)) {
                    slang = slang1;
                }
            }
            System.out.println("1. Overwrite");
            System.out.println("2. Duplicate");
            System.out.print(">> ");
            Integer lc = 0;
            try {
                lc = Integer.parseInt(bReader.readLine());
                if (lc == 1) {
                    this.sMap.put(slang, defi);
                    return 1;
                } else if (lc == 2) {
                    String dupSlang = "";
                    if (slang.indexOf("_(") == -1) {
                        dupSlang = slang + "_(1)";
                    } else {
                        int pos = slang.indexOf("_(");
                        Integer val = Integer.parseInt(slang.substring(pos + 2, pos + 3));
                        Integer val1 = val + 1;
                        dupSlang = slang.replace("_(" + val.toString(), "_(" + val1.toString());
                    }
                    this.sMap.put(dupSlang, defi);
                    return 2;
                } else {
                    return 0;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return 0;
            }
        }
        else {
            this.sMap.put(slang, defi);
            return 3;
        }
    }

    public Boolean editSlangWord(String slang, BufferedReader bReader) {
        if (!this.sMap.containsKey(slang)) {
            return false;
        }
        try {
            System.out.print("New definition: ");
            this.sMap.put(slang, bReader.readLine());
            return true;
        }
        catch(IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Boolean deleteSlangWord(String slang, BufferedReader bReader) {
        if (!this.sMap.containsKey(slang)) {
            return false;
        }
        try {
            System.out.println("Confirm delete");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print(">> ");
            Integer lc = Integer.parseInt(bReader.readLine());
            if (lc == 1) {
                this.sMap.remove(slang);
                return true;
            }
            else {
                return false;
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean resetSlangWord() {
        this.loadData("./src/main/res/data/slang.txt", "./src/main/res/data/history.dat");
        return true;
    }

    public String randomSlangWord() {
        Random random = new Random();
        int count = 0;
        int number = random.nextInt(this.sMap.size());
        for (String slang : this.sMap.keySet()) {
            if (number == count) {
                return slang;
            }
            count++;
        }
        return "";
    }

    public String randomDefinition() {
        Random random = new Random();
        int count = 0;
        int number = random.nextInt(this.sMap.size());
        for (String slang : this.sMap.keySet()) {
            if (number == count) {
                return this.sMap.get(slang);
            }
            count++;
        }
        return "";
    }

    public Boolean saveHistoryData(String hisFileName) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(hisFileName));
            objectOutputStream.writeObject(this.arrHis);
            return true;
        }
        catch(IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Boolean saveSlangDate(String slangFileName) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(slangFileName));
            objectOutputStream.writeObject(this.sMap);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getDefinition(String slang){
        try {
            String res = this.sMap.get(slang);
            if (res != null) {
                return res;
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }
}
