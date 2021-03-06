
package tingeltangel.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tingeltangel.core.Book;
import tingeltangel.core.Entry;
import tingeltangel.core.Properties;
import tingeltangel.core.Translator;
import tingeltangel.core.scripting.SyntaxError;

public class PlayerFrame extends JInternalFrame implements EntryListener, ActionListener {
    
    private final MasterFrame frame;
    private Entry entry = null;
    private JLabel description = new JLabel();
    private JLabel length = new JLabel();
    private JLabel tingID = new JLabel();
    private JLabel codeID = new JLabel();
    private JTextArea playlist = new JTextArea();
	private boolean buttonsEnabled;
    
    public PlayerFrame(Book book, final MasterFrame frame) {
        super("Player", true, true, true, true);
        this.frame = frame;
        setVisible(true);
        setBounds(610, 160, 300, 180);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        
        buttonsEnabled = Properties.getPropertyAsBoolean(Properties.WIN_MPG123+Properties._ENABLED);
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 3));
        JButton play = new JButton("PLAY");
        play.setActionCommand("play");
        play.addActionListener(this);
        buttons.add(play);
        JButton stop = new JButton("STOP");
        stop.setActionCommand("stop");
        stop.addActionListener(this);
        buttons.add(stop);
        buttons.setEnabled(buttonsEnabled);
        stop.setEnabled(buttonsEnabled);
        play.setEnabled(buttonsEnabled);
        
        JPanel props = new JPanel();
        props.setLayout(new GridLayout(4, 2));
        props.add(new JLabel("Bemerkung:"));
        props.add(description);
        props.add(new JLabel("Tracklänge:"));
        props.add(length);
        props.add(new JLabel("Ting ID:"));
        props.add(tingID);
        props.add(new JLabel("Code ID:"));
        props.add(codeID);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(props, BorderLayout.NORTH);
        panel.add(new JScrollPane(playlist), BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);
        playlist.setEnabled(false);
        
        setContentPane(panel);
        frame.getMP3Player().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playlist.setText(frame.getMP3Player().getCurrent() + "\n" + frame.getMP3Player().getPlaylist()); 
            }
        });
        frame.addEntryListener(this);
    }

    @Override
    public void entrySelected(Entry entry) {
        this.entry = entry;
        description.setText(entry.getHint());
        if(entry.isMP3() && (entry.getLength() >= 0)) {
            length.setText(Float.toString(entry.getLength()));
        } else {
            length.setText("-");
        }
        tingID.setText(Integer.toString(entry.getTingID()));
        int _codeID = Translator.ting2code(entry.getTingID());
        if(_codeID >= 0) {
            codeID.setText(Integer.toString(_codeID));
        } else {
            codeID.setText("unbekannt");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("play")) {
            if(entry != null) {
                if(entry.isMP3() && (entry.getMP3() != null)) {
                    String hint = Integer.toString(entry.getTingID());
                    String indexHint = entry.getHint();
                    if(!indexHint.isEmpty()) {
                        hint += " (" + indexHint + ")";
                    }
                    frame.getMP3Player().add(entry.getMP3(), hint);
                } else if((entry.isCode() || entry.isSub()) && (entry.getScript() != null)) {
                    try {
                        entry.getScript().execute();
                    } catch(SyntaxError se) {
                        JOptionPane.showMessageDialog(this, "Syntax Fehler in Zeile " + se.getRow() + " (" + se.getTingID() + ") (" + se.getMessage() + ")", "Syntax Fehler", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        } else if(e.getActionCommand().equals("stop")) {
            if(entry != null) {
                frame.getMP3Player().stopAndClean();
            }
        }
    }
    
    
}