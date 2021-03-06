package tingeltangel.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import tingeltangel.gui.MasterFrameMenu;

public class Translator {

    private final static String ID_TRANS_FILE = "/id_trans.data";
    
    private final static int[] code2ting = new int[0x010001];
    private final static int[] ting2code = new int[0x010001];
    
    private final static int min_object_code = 15001;
    private final static int max_object_code;
    
    private final static Random rnd = new Random();
    
    static {
        for(int i = 0; i < code2ting.length; i++) {
            code2ting[i] = -1;
            ting2code[i] = -1;
        }
        int r = 0;
        int codeId = -1;
        int lastLowerTingId = -1;
        int lastHigherTingId = -1;
        int lastCurrentCodeId = -1;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(Translator.class.getResourceAsStream(ID_TRANS_FILE)));
            String row;
            while((row = in.readLine()) != null) {
                r++;
                row = row.trim();
                if((!row.isEmpty()) && (!row.startsWith("#"))) {
                    if(row.startsWith("set ")) {
                        row = row.substring("set ".length()).trim();
                        int p = row.indexOf(" ");
                        if(p == -1) {
                            throw new Error("error in '" + ID_TRANS_FILE + "' on line " + r);
                        }
                        codeId = Integer.parseInt(row.substring(0, p).trim());
                        row = row.substring(p).trim();
                        p = row.indexOf(" ");
                        if(p == -1) {
                            throw new Error("error in '" + ID_TRANS_FILE + "' on line " + r);
                        }
                        lastLowerTingId = Integer.parseInt(row.substring(0, p).trim());
                        lastHigherTingId = Integer.parseInt(row.substring(p + 1).trim()) - 1;
                        
                    } else {
                        
                        int currentCodeId = Integer.parseInt(row);
                        if(lastCurrentCodeId >= currentCodeId) {
                            throw new Error();
                        }
                        lastCurrentCodeId = currentCodeId;
                        
                        for(int i = codeId; i < currentCodeId; i++) {
                            code2ting[i] = ++lastHigherTingId;
                        }
                        code2ting[currentCodeId] = ++lastLowerTingId;
                        codeId = currentCodeId + 1;
                    }
                }
            }
            for(int c = 0; c < 0x010000; c++) {
                if(code2ting[c] != -1) {
                    if(ting2code[code2ting[c]] == -1) {
                        ting2code[code2ting[c]] = c;
                    } else {
                        throw new Error();
                    }
                }
            }
            int moc = 0;
            for(int t = min_object_code; t <= 0x010000; t++) {
                if(ting2code[t] == -1) {
                    moc = t - 1;
                    break;
                }
            }
            max_object_code = moc;
            
        } catch(Exception e) {
            throw new Error(e);
        }
    }
    
    public static int getMinObjectCode() {
        return(min_object_code);
    }
    
    public static int getMaxObjectCode() {
        return(max_object_code);
    }
    
    public static int code2ting(int c) {
        return(code2ting[c]);
    }
    
    public static int ting2code(int t) {
        return(ting2code[t]);
    }
    
    public static int getRandomBookCode() {
        int min = 8001;
        int max = 8500;
        max = Math.min(getMaxTingCodeFrom(min), max);
        return(rnd.nextInt(max - min + 1) + min);
    }
    
    
    
    public static void main(String[] args) {
        int c = 0;
        System.out.println("^ Ting-ID ^ Code-ID ^ Ting-ID ^ Code-ID ^ Ting-ID ^ Code-ID ^ Ting-ID ^ Code-ID ^ Ting-ID ^ Code-ID ^");
        for(int i = 0; i < 0x10000; i++) {
            if(ting2code[i] >= 0) {
                System.out.print("| " + i + " | " + ting2code[i] + " ");
                c++;
                if(c == 5) {
                    c = 0;
                    System.out.println("|");
                }
            }
        }
    }

    public static int getMaxTingCodeFrom(int start) {
        while(start < 0x10000) {
            if(ting2code[start] == -1) {
                return(start - 1);
            }
            start++;
        }
        return(0xffff);
    }
}
