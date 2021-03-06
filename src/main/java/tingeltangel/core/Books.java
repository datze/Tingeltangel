
package tingeltangel.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import tingeltangel.Tingeltangel;
import tingeltangel.core.constants.TxtFile;

public class Books {
    
    private final static String KNOWN_BOOKS_FILE = "/known_books.txt";
    
    private final static HashMap<Integer, HashMap<String, String>> BOOKS = new HashMap<Integer, HashMap<String, String>>();
    
    static {
        init();
    }
    
    private static void init() {
        try {
            BOOKS.clear();
            File booksDir = new File("books");
            if(!booksDir.canRead()) {
                throw new Error("can't read from books directory");
            }
            if(!booksDir.canWrite()) {
                throw new Error("can't write to books directory");
            }
            List<File> bookFiles = Arrays.asList(booksDir.listFiles());
            for(File bookFile: bookFiles) {
            	String name = bookFile.getName();
                if(name.endsWith(".txt")) {
                    int id = Integer.parseInt(name.substring(0, name.indexOf("_")));
                    BufferedReader in = new BufferedReader(new FileReader(bookFile));
                    HashMap<String, String> data = new HashMap<String, String>();
                    String row;
                    while((row = in.readLine()) != null) {
                        row = row.trim();
                        if(!row.isEmpty()) {
                            int p = row.indexOf(":");
                            if(p>0) {
	                            String key = row.substring(0, p).trim();
	                            String value = row.substring(p + 1).trim();
	                            data.put(key, value);
                            } else {
                            	System.err.println(String.format("skipping broken line in %s", name));
                            	continue;
                            }
                        }
                    }
                    in.close();
                    // check if txt-file is valid
                    if(data.containsKey("Name")) {
                    	System.out.println(String.format("Imported book %s", data.get("Name")));
                    	BOOKS.put(id, data);
                    } else {
                    	// delete broken txt-file
                    	System.out.println(String.format("Deleted broken file %s", name));
                    	bookFile.delete();
                    }
                }
            }
        } catch(IOException ioe) {
            throw new Error(ioe);
        }
    }
    
    public static Integer[] getIDs() {
        Integer[] bks = BOOKS.keySet().toArray(new Integer[0]);
        Arrays.sort(bks);
        return(bks);
    }
    
    public static HashMap<String, String> getBook(int id) {
        return(BOOKS.get(id));
    }

    public static void search() {
        
        byte[] buffer = new byte[4096];
        
        for(int id = 0; id <= 15000; id++) {
            InputStream in = null;
            OutputStream out = null;
            try {
            	String _id=Tools.addPadding(id);
                in = new URL(Tingeltangel.BASE_URL + "/get-description/id/" + _id + "/area/en").openStream();
                out = new FileOutputStream(new File(new File("books"), _id + TxtFile._EN_TXT));
                
                int k;
                while((k = in.read(buffer)) != -1) {
                    out.write(buffer, 0, k);
                }
                
                in.close();
                out.close();
            } catch(IOException ioe) {
                if(in != null) {
                    try {
                        in.close();
                    } catch(Exception e) {
                    }
                }
                if(out != null) {
                    try {
                        out.close();
                    } catch(Exception e) {
                    }
                }
                System.out.println("id=" + id + ": " + ioe.getMessage());
            }
        }
        init();
    }

    public static void quickSearch(final Thread done) throws IOException {
        final File booksDir = new File("books");
        
        final HashSet<String> toDownload = new HashSet<String>();
        BufferedReader bin = new BufferedReader(new InputStreamReader(Books.class.getResourceAsStream(KNOWN_BOOKS_FILE)));
        String row;
        while((row = bin.readLine()) != null) {
            row = row.trim();
            if((!row.isEmpty()) && (!row.startsWith("#"))) {
                if(BOOKS.get(Integer.parseInt(row)) == null) {
                    toDownload.add(row);
                }
            }
        }
        bin.close();
        
        final JFrame splash = new JFrame("initialisieren...");
        splash.setResizable(false);
        splash.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        splash.setBounds(100, 100, 300, 100);
        final JProgressBar bar = new JProgressBar();
        bar.setMaximum(toDownload.size());
        bar.setValue(0);
        bar.setBounds(10, 160, 280, 20);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Bücher herunterladen...");
        label.setBounds(30, 30, 100, 20);
        panel.add(label);
        panel.add(bar);
        splash.getContentPane().add(panel);
        splash.setVisible(true);
        
        System.out.println("need to download " + toDownload.size() + " book txt files...");
        
        new Thread() {
            public void run() {
        
                byte[] buffer = new byte[4096];
                Iterator<String> ids = toDownload.iterator();
                int c = 0;
                while(ids.hasNext()) {
                    String row = ids.next();

                    bar.setValue(c++);

                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = new URL(Tingeltangel.BASE_URL + "/get-description/id/" + row + "/area/en").openStream();
                        out = new FileOutputStream(new File(booksDir, row + TxtFile._EN_TXT));

                        int k;
                        while((k = in.read(buffer)) != -1) {
                            out.write(buffer, 0, k);
                        }

                        in.close();
                        out.close();

                    } catch(IOException ioe) {
                        if(in != null) {
                            try {
                                in.close();
                            } catch(Exception e) {
                            }
                        }
                        if(out != null) {
                            try {
                                out.close();
                            } catch(Exception e) {
                            }
                        }
                        new File(booksDir, row + TxtFile._EN_TXT).delete();
                        System.out.println("failed to load book " + row + ": " + ioe.getMessage());
                    }
                }
                System.out.println("got " + toDownload.size() + " book txt files");
                splash.dispose();
                done.start();
            }
        }.start();
    }

	public static void update() throws IOException {
        byte[] buffer = new byte[4096];
        
        Integer[] ids = getIDs();
        
        for(int r = 0; r < ids.length; r++) {
            int id = ids[r];
            
            String _id=Tools.addPadding(id);
            InputStream in = new URL(Tingeltangel.BASE_URL + "/get-description/id/" + _id + "/area/en").openStream();
            OutputStream out = new FileOutputStream(new File(new File("books"), _id + TxtFile._EN_TXT));

            int k;
            while((k = in.read(buffer)) != -1) {
                out.write(buffer, 0, k);
            }

            in.close();
            out.close();
            
        }
        init();
    }
    
    
    public static void main(String[] args) throws Exception {
        Integer[] ids = getIDs();
        System.out.println("^ Buch ID ^ Name ^ Herausgeber ^ Autor ^ Version ^ URL ^ Ländercode ^ Downloads ^^^^");
        for(int i = 0; i < ids.length; i++) {
            HashMap<String, String> book = Books.getBook(ids[i]);
            String id=Tools.addPadding(ids[i]);
            System.out.print("| " + id + " | " + book.get("Name") + " | ");
            System.out.print(book.get("Publisher") + " | " + book.get("Author") + " | ");
            System.out.print(book.get("Version") + " | " + book.get("URL") + " | " + book.get("Area Code") + " | ");
            System.out.print("[[http://system.ting.eu/book-files/get-description/id/" + id + "/area/en|txt-Datei]]" + " | ");
            System.out.print("[[http://system.ting.eu/book-files/get/id/" + id + "/area/en/type/thumb|png-Datei]]" + " | ");
            System.out.print("[[http://system.ting.eu/book-files/get/id/" + id + "/area/en/type/archive|ouf-Datei]]" + " | ");
            System.out.println("[[http://system.ting.eu/book-files/get/id/" + id + "/area/en/type/script|src-Datei]]" + " |");
        }
    }
    
}
