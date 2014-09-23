/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prodigy4440.view;

import com.prodigy4440.model.JFontChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Normalizer;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author prodigy4440
 */
public class MainJFrame extends JFrame {
    
    private static final long serialVersionUID = 189463489476457893L;

    //Menu bar declaration
    private JMenuBar menuBar;

    //Menus declarations
    private JMenu fileJMenu;
    private JMenu editJMenu;
    private JMenu formatJMenu;
    private JMenu viewJMenu;
    private JMenu helpJMenu;

    //File menu itens declaration
    private JMenuItem newDocumentJMenuItem;
    private JMenuItem openJMenuItem;
    private JMenuItem saveJMenuItem;
    private JMenuItem printJMenuItem;
    private JMenuItem exitJMenuItem;

    //edit menu items 
    private JMenuItem undoJMenuItem;
    private JMenuItem redoJMenuItem;
    private JMenuItem copyJMenuItem;
    private JMenuItem cutJMenuItem;
    private JMenuItem pasteJMenuItem;
    private JMenuItem deleteJMenuItem;
    private JMenuItem selectAllJMenuItem;
    private JMenuItem findJMenuItem;
    private JMenuItem replaceJMenuItem;

    //declaring format menu items
    private JCheckBoxMenuItem wordWrapJCheckBoxMenuItem;
    private JMenuItem fontJMenuItem;
    private JMenuItem colorJMenuItem;

    //view menu items 
    private JCheckBoxMenuItem statusBarJCheckBoxMenuItem;

    //helpMenu litems
    private JMenuItem helpJMenuItem;
    private JMenuItem aboutJMenuItem;

    //status panel
    private JPanel southJPanel;
    private JLabel statusJLabel;

    //The main text area
    public JTextArea textArea;

    //Undo manager for undoing actions
    private UndoManager undoManager;
    
    private Font font;
    private Color color;
    
    private Document document;
    
    public WordSearcher wordSearcher;
    
    public MainJFrame() {
        initComponents();
    }
    
    public MainJFrame(File file) {
        initComponents();
    }
    
    public final void initComponents() {
        
        List<Image> icons = new LinkedList<Image>();
        icons.add(new ImageIcon(getClass().getResource("/com/prodigy4440/Yted16x16.png")).getImage());
        icons.add(new ImageIcon(getClass().getResource("/com/prodigy4440/Yted32x32.png")).getImage());
        icons.add(new ImageIcon(getClass().getResource("/com/prodigy4440/Yted48x48.png")).getImage());
        icons.add(new ImageIcon(getClass().getResource("/com/prodigy4440/Yted72x72.png")).getImage());
        
        this.setIconImages(icons);
        
        ActionHandler actionHandler = new ActionHandler(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(620, 520);
        this.setLocationRelativeTo(null);
        this.setTitle("Untitled Document- YorTextEditor");
        southJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        SoftBevelBorder sbb = new SoftBevelBorder(SoftBevelBorder.LOWERED);
        southJPanel.setBorder(sbb);
        menuBar = new JMenuBar();
        
        fileJMenu = new JMenu("File");
        fileJMenu.setMnemonic('F');
        editJMenu = new JMenu("Edit");
        editJMenu.setMnemonic('E');
        formatJMenu = new JMenu("Format");
        formatJMenu.setMnemonic('A');
        viewJMenu = new JMenu("View");
        viewJMenu.setMnemonic('V');
        helpJMenu = new JMenu("Help");
        helpJMenu.setMnemonic('H');
        
        newDocumentJMenuItem = new JMenuItem("New");
        newDocumentJMenuItem.addActionListener(actionHandler);
        newDocumentJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        openJMenuItem = new JMenuItem("Open");
        openJMenuItem.addActionListener(actionHandler);
        openJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
        saveJMenuItem = new JMenuItem("Save");
        saveJMenuItem.addActionListener(actionHandler);
        saveJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        printJMenuItem = new JMenuItem("Print");
        printJMenuItem.addActionListener(actionHandler);
        printJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
        exitJMenuItem = new JMenuItem("Exit");
        exitJMenuItem.addActionListener(actionHandler);
        
        undoJMenuItem = new JMenuItem("Undo");
        undoJMenuItem.addActionListener(actionHandler);
        undoJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));
        redoJMenuItem = new JMenuItem("Redo");
        redoJMenuItem.addActionListener(actionHandler);
        redoJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK));
        copyJMenuItem = new JMenuItem("Copy");
        copyJMenuItem.addActionListener(actionHandler);
        copyJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
        cutJMenuItem = new JMenuItem("Cut");
        cutJMenuItem.addActionListener(actionHandler);
        cutJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        pasteJMenuItem = new JMenuItem("Paste");
        pasteJMenuItem.addActionListener(actionHandler);
        pasteJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));
        deleteJMenuItem = new JMenuItem("Delete");
        deleteJMenuItem.addActionListener(actionHandler);
        deleteJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
        selectAllJMenuItem = new JMenuItem("Select All");
        selectAllJMenuItem.addActionListener(actionHandler);
        selectAllJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
        findJMenuItem = new JMenuItem("Find");
        findJMenuItem.addActionListener(actionHandler);
        findJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK));
        replaceJMenuItem = new JMenuItem("Replace");
        replaceJMenuItem.addActionListener(actionHandler);
        replaceJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
        
        wordWrapJCheckBoxMenuItem = new JCheckBoxMenuItem("Word Wrap");
        wordWrapJCheckBoxMenuItem.addActionListener(actionHandler);
        wordWrapJCheckBoxMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
        fontJMenuItem = new JMenuItem("Font");
        fontJMenuItem.addActionListener(actionHandler);
        colorJMenuItem = new JMenuItem("Color");
        colorJMenuItem.addActionListener(actionHandler);
        
        statusBarJCheckBoxMenuItem = new JCheckBoxMenuItem("Status Bar");
        statusBarJCheckBoxMenuItem.addActionListener(actionHandler);
        statusBarJCheckBoxMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.ALT_MASK));
        
        helpJMenuItem = new JMenuItem("Help");
        helpJMenuItem.addActionListener(actionHandler);
        helpJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, Event.CTRL_MASK));
        aboutJMenuItem = new JMenuItem("About");
        aboutJMenuItem.addActionListener(actionHandler);
        
        statusJLabel = new JLabel("Status:");

        //Main text area setup
        textArea = new JTextArea();
        undoManager = new UndoManager();
        wordSearcher = new WordSearcher(textArea);
        textArea.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
        document = textArea.getDocument();
        document.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        
        font = new Font("Tahoma", Font.PLAIN, 16);
        textArea.setFont(font);
        color = Color.BLUE;
        textArea.setForeground(color);
        
        undoManager = new UndoManager();
        
        fileJMenu.add(newDocumentJMenuItem);
        fileJMenu.addSeparator();
        fileJMenu.add(openJMenuItem);
        fileJMenu.add(saveJMenuItem);
        fileJMenu.addSeparator();
        fileJMenu.add(printJMenuItem);
        fileJMenu.addSeparator();
        fileJMenu.add(exitJMenuItem);
        
        editJMenu.add(undoJMenuItem);
        editJMenu.add(redoJMenuItem);
        editJMenu.addSeparator();
        editJMenu.add(copyJMenuItem);
        editJMenu.add(cutJMenuItem);
        editJMenu.add(pasteJMenuItem);
        editJMenu.addSeparator();
        editJMenu.add(deleteJMenuItem);
        editJMenu.add(selectAllJMenuItem);
        editJMenu.addSeparator();
        editJMenu.add(findJMenuItem);
        editJMenu.add(replaceJMenuItem);
        
        formatJMenu.add(wordWrapJCheckBoxMenuItem);
        formatJMenu.add(fontJMenuItem);
        formatJMenu.add(colorJMenuItem);
        
        viewJMenu.add(statusBarJCheckBoxMenuItem);
        
        helpJMenu.add(helpJMenuItem);
        helpJMenu.add(aboutJMenuItem);
        
        menuBar.add(fileJMenu);
        menuBar.add(editJMenu);
        menuBar.add(formatJMenu);
        menuBar.add(viewJMenu);
        menuBar.add(helpJMenu);
        
        southJPanel.setVisible(false);
        southJPanel.add(statusJLabel);
        //JScrollPane setup
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //setting uo the Jframe
        this.setJMenuBar(menuBar);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(southJPanel, BorderLayout.SOUTH);
        textArea.addMouseListener(new MouseInputListener() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                Highlighter h = textArea.getHighlighter();
                h.removeAllHighlights();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                Highlighter h = textArea.getHighlighter();
                h.removeAllHighlights();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
        
        textArea.addKeyListener(new KeyListener() {
            
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isControlDown() & e.getKeyChar() == '\'') {
                    
                    try {
                        String text = textArea.getText(textArea.getCaretPosition() - 1, 1);
                        String normalize = Normalizer.normalize(text + "\u0300", Normalizer.Form.NFC);
                        textArea.insert(normalize, textArea.getCaretPosition() - 1);
                        document.remove(textArea.getCaretPosition() - 1, 1);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } else if (e.isAltDown() & e.getKeyChar() == '\'') {
                    try {
                        String text = textArea.getText(textArea.getCaretPosition() - 1, 1);
                        String normalize = Normalizer.normalize(text + "\u0301", Normalizer.Form.NFC);
                        textArea.insert(normalize, textArea.getCaretPosition() - 1);
                        document.remove(textArea.getCaretPosition() - 1, 1);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (e.getKeyChar() == 'c') {
                    e.consume();
                    textArea.insert("\u1eb9", textArea.getCaretPosition());
                } else if (e.getKeyChar() == 'C') {
                    e.consume();
                    textArea.insert("\u1Eb8", textArea.getCaretPosition());
                } else if (e.getKeyChar() == 'q') {
                    e.consume();
                    textArea.insert("\u1E63", textArea.getCaretPosition());
                } else if (e.getKeyChar() == 'Q') {
                    e.consume();
                    textArea.insert("\u1E62", textArea.getCaretPosition());
                } else if (e.getKeyChar() == 'v') {
                    e.consume();
                    textArea.insert("\u1ECd", textArea.getCaretPosition());
                } else if (e.getKeyChar() == 'V') {
                    e.consume();
                    textArea.insert("\u1ECC", textArea.getCaretPosition());
                } else if (e.getKeyChar() == 'x') {
                    e.consume();
                    try {
                        String text = textArea.getText(textArea.getCaretPosition() - 1, 1);
                        String normalize = Normalizer.normalize(text + "\u0301", Normalizer.Form.NFC);
                        textArea.insert(normalize, textArea.getCaretPosition() - 1);
                        document.remove(textArea.getCaretPosition() - 1, 1);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (e.getKeyChar() == 'X') {
                    e.consume();
                    try {
                        String text = textArea.getText(textArea.getCaretPosition() - 1, 1);
                        String normalize = Normalizer.normalize(text + "\u0301", Normalizer.Form.NFC);
                        textArea.insert(normalize, textArea.getCaretPosition() - 1);
                        document.remove(textArea.getCaretPosition() - 1, 1);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (e.getKeyChar() == 'z') {
                    e.consume();
                    try {
                        String text = textArea.getText(textArea.getCaretPosition() - 1, 1);
                        String normalize = Normalizer.normalize(text + "\u0300", Normalizer.Form.NFC);
                        textArea.insert(normalize, textArea.getCaretPosition() - 1);
                        document.remove(textArea.getCaretPosition() - 1, 1);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (e.getKeyChar() == 'Z') {
                    e.consume();
                    try {
                        String text = textArea.getText(textArea.getCaretPosition() - 1, 1);
                        String normalize = Normalizer.normalize(text + "\u0300", Normalizer.Form.NFC);
                        textArea.insert(normalize, textArea.getCaretPosition() - 1);
                        document.remove(textArea.getCaretPosition() - 1, 1);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                String line = textArea.getText();
                long words = wordCount(line);
                int count = line.length();
                
                statusJLabel.setText("Line : " + textArea.getLineCount() + " Words : " + words + " Characters : " + count);
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                Highlighter h = textArea.getHighlighter();
                h.removeAllHighlights();
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });
        
    }
    
    public class ActionHandler implements ActionListener {
        
        private final MainJFrame mainJFrame;
        
        public ActionHandler(MainJFrame mainJFrame) {
            this.mainJFrame = mainJFrame;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newDocumentJMenuItem) {
                textArea.setText("");
                mainJFrame.setTitle("Untitled - YorTextEditor");
            } else if (e.getSource() == openJMenuItem) {
                
                if (!textArea.getText().isEmpty()) {
                    textArea.setText("");
                }
                
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home", "."));
                FileFilter txtFilter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                fileChooser.setFileFilter(txtFilter);
                
                int showOpenDialog = fileChooser.showOpenDialog(mainJFrame);
                
                if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        List<String> readLines = FileUtils.readLines(selectedFile, "UTF-8");
                        for (String string : readLines) {
                            textArea.append(string + "\n");
                        }
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(mainJFrame, "File not Found", "File Status", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(mainJFrame, "IO Error occured", "File Status", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    mainJFrame.setTitle(selectedFile.getName() + " - YorTextEditor");
                    statusJLabel.setText("Done opening");
                }
            } else if (e.getSource() == saveJMenuItem) {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
                int showSaveDialog = fileChooser.showSaveDialog(mainJFrame);
                if (showSaveDialog == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String withExtension = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!withExtension.toLowerCase().endsWith(".txt")) {
                        withExtension += ".txt";
                        selectedFile = new File(withExtension);
                    }
                    if (!selectedFile.exists()) {
                        try {
                            selectedFile.createNewFile();
                        } catch (IOException ex) {
                            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    mainJFrame.setTitle(selectedFile.getName() + " - YorTextEditor");
                    try {
                        FileUtils.writeStringToFile(selectedFile, textArea.getText(), "UTF-8");
                    } catch (IOException ex) {
                        Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                statusJLabel.setText("Saved");
            } else if (e.getSource() == printJMenuItem) {
                try {
                    textArea.print();
                    statusJLabel.setText("Done printing");
                } catch (PrinterException ex) {
                    Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (e.getSource() == exitJMenuItem) {
                System.exit(0);
            } else if (e.getSource() == undoJMenuItem) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException cue) {
                    
                }
            } else if (e.getSource() == redoJMenuItem) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                } catch (CannotRedoException cre) {
                    
                }
            } else if (e.getSource() == copyJMenuItem) {
                textArea.copy();
            } else if (e.getSource() == cutJMenuItem) {
                textArea.cut();
            } else if (e.getSource() == pasteJMenuItem) {
                textArea.paste();
            } else if (e.getSource() == deleteJMenuItem) {
                textArea.replaceSelection("");
            } else if (e.getSource() == selectAllJMenuItem) {
                textArea.selectAll();
            } else if (e.getSource() == findJMenuItem) {
                String showInputDialog = JOptionPane.showInputDialog("Find");
                if (showInputDialog != null) {
                    String word = showInputDialog.trim();
                    int offset = wordSearcher.search(word, false, 5);
                    if (offset != -1) {
                        try {
                            textArea.scrollRectToVisible(textArea
                                    .modelToView(offset));
                        } catch (BadLocationException be) {
                        }
                    }
                }
                
            } else if (e.getSource() == replaceJMenuItem) {
                FindAndReplaceJDialog findAndReplaceJDialog = new FindAndReplaceJDialog(mainJFrame, true, mainJFrame);
                findAndReplaceJDialog.setLocationRelativeTo(mainJFrame);
                findAndReplaceJDialog.setVisible(true);
            } else if (e.getSource() == wordWrapJCheckBoxMenuItem) {
                if (wordWrapJCheckBoxMenuItem.getState()) {
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                } else {
                    textArea.setLineWrap(false);
                    textArea.setWrapStyleWord(false);
                }
            } else if (e.getSource() == fontJMenuItem) {
                JFontChooser fontChooser = new JFontChooser();
                int showDialog = fontChooser.showDialog(mainJFrame);
                if (showDialog == JFontChooser.OK_OPTION) {
                    Font selectedFont = fontChooser.getSelectedFont();
                    font = selectedFont;
                    textArea.setFont(font);
                }
            } else if (e.getSource() == colorJMenuItem) {
                Color showDialog = JColorChooser.showDialog(mainJFrame, "Choose Color", color);
                if (showDialog != null) {
                    color = showDialog;
                    textArea.setForeground(color);
                }
            } else if (e.getSource() == statusBarJCheckBoxMenuItem) {
                if (statusBarJCheckBoxMenuItem.getState()) {
                    southJPanel.setVisible(true);
                } else {
                    southJPanel.setVisible(false);
                }
            } else if (e.getSource() == helpJMenuItem) {
                HelpJDialog helpJDialog = new HelpJDialog(mainJFrame, true);
                helpJDialog.setLocationRelativeTo(mainJFrame);
                helpJDialog.setVisible(true);
            } else if (e.getSource() == aboutJMenuItem) {
                AboutJDialog aboutJDialog = new AboutJDialog(mainJFrame, true);
                aboutJDialog.setLocationRelativeTo(mainJFrame);
                aboutJDialog.setVisible(true);
            }
        }
    }
    
    public static void highlightText(JTextComponent component, String s) {
        try {
            Highlighter highlighter = component.getHighlighter();
            String text = component.getText();
            String line = null;
            int start = 0;
            int end;
            int totalLines = ((JTextArea) component).getLineCount();
            for (int i = 0; i < totalLines; i++) {
                if (i == 5) {
                    start = ((JTextArea) component).getLineOfOffset(i);
                    end = ((JTextArea) component).getLineEndOffset(i);
                    line = text.substring(start, end);
                }
                
            }
            
            int pos = start;
            if ((pos = text.indexOf(s, pos)) >= start) {
                DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.BLUE);
                highlighter.addHighlight(pos, pos + s.length(), highlightPainter);
            }
        } catch (BadLocationException ble) {
            
        }
    }
    
    class WordSearcher {
        
        protected JTextComponent comp;
        protected Highlighter.HighlightPainter painter;
        
        public WordSearcher(JTextComponent comp) {
            this.comp = comp;
            this.painter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
        }

        // Search for a word and return the offset of the
        // first occurrence. Highlights are added for all
        // occurrences found.
        public int search(String word, boolean caseSensitivity, int n) {
            int firstOffset = -1;
            Highlighter highlighter = comp.getHighlighter();

            // Remove any existing highlights for last word
            comp.getHighlighter().removeAllHighlights();
            
            if (word == null || word.equals("")) {
                return -1;
            }

            // Look for the word we are given - insensitive search
            String content = null;
            try {
                Document d = comp.getDocument();
                content = d.getText(0, d.getLength()).toLowerCase();
            } catch (BadLocationException e) {
                // Cannot happen
                return -1;
            }
            
            if (caseSensitivity) {
                
            } else {
                word = word.toLowerCase();
            }
            int lastIndex = 0;
            int wordSize = word.length();
            
            while ((lastIndex = content.indexOf(word, lastIndex)) != -1) {
                int endIndex = lastIndex + wordSize;
                try {
                    highlighter.addHighlight(lastIndex, endIndex, painter);
                    if (n != 1) {
                        
                    } else {
                        break;
                    }
                } catch (BadLocationException e) {
                    // Nothing to do
                }
                if (firstOffset == -1) {
                    firstOffset = lastIndex;
                }
                lastIndex = endIndex;
            }
            
            return firstOffset;
        }
        
    }
    
    private static long wordCount(String line) {
        
        long numWords = 0;
        
        int index = 0;
        
        boolean prevWhiteSpace = true;
        
        while (index < line.length()) {
            
            char c = line.charAt(index++);
            
            boolean currWhiteSpace = Character.isWhitespace(c);
            
            if (prevWhiteSpace && !currWhiteSpace) {
                
                numWords++;
                
            }
            
            prevWhiteSpace = currWhiteSpace;
            
        }
        
        return numWords;
        
    }
    
    public static String[] getTextLineByLine(JTextArea area) {
        int numberOfLine = area.getLineCount();
        String resultString[] = new String[numberOfLine];
        String text = area.getText();
        for (int i = 0; i < numberOfLine; i++) {
            try {
                int start = area.getLineStartOffset(i);
                int end = area.getLineEndOffset(i);
                
                String line = text.substring(start, end);
                resultString[i] = line;
            } catch (BadLocationException ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return resultString;
    }
    
}
