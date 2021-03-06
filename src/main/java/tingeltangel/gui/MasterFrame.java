
package tingeltangel.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import tingeltangel.Tingeltangel;
import tingeltangel.core.Book;
import tingeltangel.core.Books;
import tingeltangel.core.Codes;
import tingeltangel.core.Entry;
import tingeltangel.core.Importer;
import tingeltangel.core.MP3Player;
import tingeltangel.core.NoBookException;
import tingeltangel.core.Tools;
import tingeltangel.core.Translator;
import tingeltangel.core.scripting.SyntaxError;

public class MasterFrame extends JFrame implements MenuCallback {

    private MP3Player mp3Player = new MP3Player();
    private Book book = new Book(mp3Player, null);
    
    private JDesktopPane desktop;
    private IndexFrame indexFrame;
    private PlayerFrame playerFrame;
    private RegisterFrame registerFrame;
    private CodeFrame codeFrame;
    private PropertyFrame propertyFrame;
    private StickFrame stickFrame;
    private ReferenceFrame referenceFrame;
    private TranslatorFrame translatorFrame;
    
    private InfoFrame contactFrame = new InfoFrame("Kontakt", "html/contact.html");
    private InfoFrame licenseFrame = new InfoFrame("Kontakt", "html/license.html");

    
    
    public MasterFrame() {
        super(Tingeltangel.MAIN_FRAME_TITLE + Tingeltangel.MAIN_FRAME_VERSION);
        
        
        indexFrame = new IndexFrame(book, this);
        playerFrame = new PlayerFrame(book, this);
        registerFrame = new RegisterFrame(book, this);
        codeFrame = new CodeFrame(book, this);
        propertyFrame = new PropertyFrame(book, this);
        stickFrame = new StickFrame(book, this);
        referenceFrame = new ReferenceFrame(book, this);
        translatorFrame = new TranslatorFrame(this);
        
        book.addRegisterListener(registerFrame);
        
        JFrame.setDefaultLookAndFeelDecorated(true);

        setBounds(
                    Tingeltangel.MAIN_FRAME_POS_X,
                    Tingeltangel.MAIN_FRAME_POS_Y,
                    Tingeltangel.MAIN_FRAME_WIDTH + getInsets().left + getInsets().right,
                    Tingeltangel.MAIN_FRAME_HEIGHT + getInsets().top + getInsets().bottom
        );

        desktop = new JDesktopPane();
        MasterFrameMenu.setMenuCallback(this);
        setMenuBar(MasterFrameMenu.getMenuBar());
        
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeMasterFrame();
            }
        });
        setVisible(true);
        
        desktop.add(indexFrame);
        desktop.add(playerFrame);
        desktop.add(registerFrame);
        desktop.add(codeFrame);
        desktop.add(propertyFrame);
        desktop.add(stickFrame);
        desktop.add(referenceFrame);
        desktop.add(contactFrame);
        desktop.add(licenseFrame);
        desktop.add(translatorFrame);
        
        
        
        setContentPane(desktop);

    }
    
    public MP3Player getMP3Player() {
        return(mp3Player);
    }
    
    public void showReferenceFrame() {
        referenceFrame.setVisible(true);
    }
    
    public Book getBook() {
        return(book);
    }
    
    private LinkedList<EntryListener> listeners = new LinkedList<EntryListener>();
    
    void addEntryListener(EntryListener listener) {
        listeners.add(listener);
    }
    
    void entrySelected(int i) {
        Entry entry = book.getEntry(i);
        Iterator<EntryListener> it = listeners.iterator();
        while(it.hasNext()) {
            it.next().entrySelected(entry);
        }
    }
    
    private void closeMasterFrame() {
        if(book.unsaved()) {
            int value =  JOptionPane.showConfirmDialog(this, "Das aktuelle Buch ist nicht gespeichert. wollen sie trotzdem das Programm beenden?", "Frage...", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (value == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    @Override
    public void callback(String id) {
        if(id.equals("buch.exit")) {
            closeMasterFrame();
        } else if(id.equals("buch.new")) {
            if(book.unsaved()) {
                int value =  JOptionPane.showConfirmDialog(this, "Das aktuelle Buch ist nicht gespeichert. wollen sie trotzdem ein neues Buch erstellen?", "Frage...", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (value == JOptionPane.YES_OPTION) {
                    book.clear();
                }
            } else {
                book.clear();
            }
            indexFrame.update();
        } else if(id.equals("buch.import")) {
            boolean loadBook = false;
            if(book.unsaved()) {
                int value =  JOptionPane.showConfirmDialog(this, "Das aktuelle Buch ist nicht gespeichert. wollen sie trotzdem ein Buch importieren?", "Frage...", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (value == JOptionPane.YES_OPTION) {
                    loadBook = true;
                }
            } else {
                loadBook = true;
            }
            if(loadBook) {
                final JFileChooser fc = new JFileChooser();
            
                fc.setCurrentDirectory(new java.io.File("."));
                fc.setDialogTitle("Ting-Buch Verzeichniss wählen");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);

                if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        book.setDirectory(fc.getSelectedFile());
                        
                        StringCallback cb = new StringCallback() {
                            @Override
                            public void callback(String s) {
                                // extract id
                                int p = s.indexOf(" ");
                                if(p >= 0) {
                                    s = s.substring(0, p);
                                }
                                int _id = Integer.parseInt(s);
                                try {
                                    MasterFrame.this.setEnabled(false);
                                    Importer.importOfficial(_id, fc.getSelectedFile(), mp3Player, book);
                                    propertyFrame.refresh();
                                    indexFrame.update();
                                } catch(Exception e) {
                                    JOptionPane.showMessageDialog(MasterFrame.this, "Das Buch konnte nicht importiert werden");
                                    e.printStackTrace(System.out);
                                } finally {
                                    MasterFrame.this.setEnabled(true);
                                }
                            }
                        };
                        
                        String[] options;
                        Integer[] ids = Books.getIDs();
                        options = new String[ids.length];
                        for(int i = 0; i < ids.length; i++) {
                            String m=Tools.addPadding(ids[i]);
                            m += " " + Books.getBook(ids[i]).get("Name");
                            m += " (" + Books.getBook(ids[i]).get("Author") + ")";
                            options[i] = m;
                        }
                        MultipleChoiceDialog.showDropdown(this, "MID auswählen", "Bitte wähle eine Buch-ID aus", "OK", options, 0, cb);
                        
                        
                        
                    } catch(Exception e) {
                        JOptionPane.showMessageDialog(this, "Das Buch konnte nicht importiert werden");
                        e.printStackTrace(System.out);
                    }
                }
            }
        } else if(id.equals("buch.load")) {
            boolean loadBook = false;
            if(book.unsaved()) {
                int value =  JOptionPane.showConfirmDialog(this, "Das aktuelle Buch ist nicht gespeichert. wollen sie trotzdem ein Buch laden?", "Frage...", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (value == JOptionPane.YES_OPTION) {
                    loadBook = true;
                }
            } else {
                loadBook = true;
            }
            if(loadBook) {
                JFileChooser fc = new JFileChooser();
                
                fc.setCurrentDirectory(new java.io.File("."));
                fc.setDialogTitle("Ting-Buch öffnen");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);
              
                if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        book.setDirectory(fc.getSelectedFile());
                        Book.load(new File(fc.getSelectedFile(), "book.tbu"), book);
                    } catch(FileNotFoundException e) {
                        JOptionPane.showMessageDialog(this, "Das Buch konnte nicht gefunden werden");
                        e.printStackTrace(System.out);
                    } catch(IOException e) {
                        JOptionPane.showMessageDialog(this, "Das Buch konnte nicht geladen werden");
                        e.printStackTrace(System.out);
                    } catch (NoBookException ex) {
                        JOptionPane.showMessageDialog(this, "Ein unbekannter Fehler ist aufgetreten");
                        ex.printStackTrace(System.out);
                    }
                }
            }
            
            propertyFrame.refresh();
            indexFrame.update();
            
        } else if(id.equals("buch.save")) {
            
            
            if(!book.hasDirectory()) {
                callback("buch.saveas");
            } else {
                try {
                    book.save();
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(this, "Das Buch konnte nicht gespeichert werden");
                    e.printStackTrace(System.out);
                }
            }
            
        } else if(id.equals("buch.saveas")) {
            JFileChooser fc = new JFileChooser();
            
            fc.setCurrentDirectory(new java.io.File("."));
            fc.setDialogTitle("Ting-Buch speichern");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            
            if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    book.save(new File(fc.getSelectedFile(), "book.tbu"));
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(this, "Das Buch konnte nicht gespeichert werden");
                    e.printStackTrace(System.out);
                }
            }
        } else if(id.equals("buch.generate")) {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setDialogTitle("Speicherverzeichniss auswählen (Ting Dateien)");
            if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File dir = fc.getSelectedFile();
                if(!dir.exists()) {
                    JOptionPane.showMessageDialog(this, "Das Verzeichniss konnte nicht gefunden werden");
                } else if(!dir.isDirectory()) {
                    JOptionPane.showMessageDialog(this, "Kein Verzeichniss ausgewählt");
                } else {
                    try {
                        book.export(dir);
                    } catch(IOException e) {
                        JOptionPane.showMessageDialog(this, "Buchgenerierung fehlgeschlagen");
                        e.printStackTrace(System.out);
                    } catch(IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(this, "Buchgenerierung fehlgeschlagen: " + e.getMessage());
                    } catch(SyntaxError e) {
                        e.printStackTrace(System.out);
                        JOptionPane.showMessageDialog(this, "Buchgenerierung fehlgeschlagen: Syntax Error in Skript " + e.getTingID() + " in Zeile " + e.getRow() + " (" + e.getMessage() + ")");
                    }
                }
            }
        } else if(id.startsWith("buch.generateEpsCodes.")) {
            if(id.endsWith(".600")) {
                Codes.setResolution(Codes.DPI600);
            } else {
                Codes.setResolution(Codes.DPI1200);
            }
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setDialogTitle("Speicherverzeichniss auswählen (eps Dateien)");
            if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File dir = fc.getSelectedFile();
                if(!dir.exists()) {
                    JOptionPane.showMessageDialog(this, "Das Verzeichniss konnte nicht gefunden werden");
                } else if(!dir.isDirectory()) {
                    JOptionPane.showMessageDialog(this, "Kein Verzeichniss ausgewählt");
                } else {
                    try {
                        book.epsExport(dir);
                    } catch(IOException e) {
                        JOptionPane.showMessageDialog(this, "eps-Generierung fehlgeschlagen");
                        e.printStackTrace(System.out);
                    } catch(IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(this, "eps-Generierung fehlgeschlagen: " + e.getMessage());
                    }
                }
            }
        } else if(id.startsWith("buch.generatePngCodes.")) {
            if(id.endsWith(".600")) {
                Codes.setResolution(Codes.DPI600);
            } else {
                Codes.setResolution(Codes.DPI1200);
            }
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setDialogTitle("Speicherverzeichniss auswählen (png Dateien)");
            if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File dir = fc.getSelectedFile();
                if(!dir.exists()) {
                    JOptionPane.showMessageDialog(this, "Das Verzeichniss konnte nicht gefunden werden");
                } else if(!dir.isDirectory()) {
                    JOptionPane.showMessageDialog(this, "Kein Verzeichniss ausgewählt");
                } else {
                    try {
                        book.pngExport(dir);
                    } catch(IOException e) {
                        JOptionPane.showMessageDialog(this, "png-Generierung fehlgeschlagen");
                        e.printStackTrace(System.out);
                    } catch(IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(this, "png-Generierung fehlgeschlagen: " + e.getMessage());
                    }
                }
            }
        } else if(id.equals("windows.stick")) {
            stickFrame.setVisible(true);
        } else if(id.equals("windows.player")) {
            playerFrame.setVisible(true);
        } else if(id.equals("windows.properties")) {
            propertyFrame.setVisible(true);
        } else if(id.equals("windows.code")) {
            codeFrame.setVisible(true);
        } else if(id.equals("windows.register")) {
            registerFrame.setVisible(true);
        } else if(id.equals("windows.index")) {
            indexFrame.setVisible(true);
        } else if(id.equals("windows.reference")) {
            referenceFrame.setVisible(true);
        } else if(id.equals("windows.translator")) {
            translatorFrame.setVisible(true);
        } else if(id.startsWith("codes.raw.")) {
            id = id.substring("codes.raw.".length());
            int start = Integer.parseInt(id.substring(0, 1)) * 10000 + Integer.parseInt(id.substring(2)) * 1000;
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Code Tabelle (*.ps)", "ps"));
            if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    String file = fc.getSelectedFile().getCanonicalPath();
                    if(!file.toLowerCase().endsWith(".ps")) {
                        file = file + ".ps";
                    }
                    PrintWriter out = new PrintWriter(new FileWriter(file));
                    Codes.drawPage(start, out);
                    out.close();
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(this, "Die Codetabelle konnte nicht gespeichert werden");
                    e.printStackTrace(System.out);
                }
            }
        } else if(id.startsWith("codes.ting.")) {
            if(!genTingCodes(Integer.parseInt(id.substring(id.lastIndexOf(".") + 1)))) {
                JOptionPane.showMessageDialog(this, "Die Ting-Codetabelle konnte nicht erstellt werden, da die gewählten Codes noch unbekannt sind.");
            }
        } else if(id.equals("codes.tabular.ting2code")) {
            generateTabular(true);
        } else if(id.equals("codes.tabular.code2ting")) {
            generateTabular(false);
        } else if(id.equals("books.search")) {
            Books.search();
        } else if(id.equals("books.update")) {
            try {
                Books.update();
            } catch(IOException ioe) {
                ioe.printStackTrace(System.out);
                JOptionPane.showMessageDialog(this, "Update der bekannten Bücher fehlgeschlagen: " + ioe.getMessage());
            }
        } else if(id.equals("about.contact")) {
            contactFrame.setVisible(true);
        } else if(id.equals("about.license")) {
            licenseFrame.setVisible(true);
        }
    }
    
    private void generateTabular(boolean ting2code) {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Tabelle (*.txt)", "txt"));
        if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String file = fc.getSelectedFile().getCanonicalPath();
                if(!file.toLowerCase().endsWith(".txt")) {
                    file = file + ".txt";
                }
                PrintWriter out = new PrintWriter(new FileWriter(file));
                for(int i = 0; i < 0x10000; i++) {
                    int t = Translator.code2ting(i);
                    if(ting2code) {
                        t = Translator.ting2code(i);
                    }
                    if(t != -1) {
                        out.println(i + "\t" + t);
                    }
                }
                out.close();
            } catch(Exception e) {
                JOptionPane.showMessageDialog(this, "Die Tabelle konnte nicht gespeichert werden");
                e.printStackTrace(System.out);
            }
        }
    }
    
    private boolean genTingCodes(int start) {
        boolean found = false;
        for(int i = start; i < start + 1000; i++) {
            if(Translator.ting2code(i) != -1) {
                found = true;
                break;
            }
        }
        if(!found) {
            return(false);
        }
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("PostScript Datei (*.ps)", "ps"));
        fc.setDialogTitle("Zieldatei auswählen");
        if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String file = fc.getSelectedFile().getCanonicalPath();
                if(!file.toLowerCase().endsWith(".ps")) {
                    file = file + ".ps";
                }
                
                
                int[] idx = new int[1000];
                
                
                String[] lbs = new String[idx.length];
                for(int i = start; i < start + 1000; i++) {
                    int code = Translator.ting2code(i);
                    idx[i - start] = code;
                    lbs[i - start] = "" + i;
                }
                PrintWriter out = new PrintWriter(new FileWriter(file));
                Codes.drawPage(idx, lbs, out);
                out.close();
            } catch(IOException ioe) {
                JOptionPane.showMessageDialog(this, "Codegenerierung fehlgeschlagen");
                ioe.printStackTrace(System.out);
            }
        }
        return(true);
    }
    
}
