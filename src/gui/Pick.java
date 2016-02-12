/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
package gui;

import java.io.*;
import java.net.MalformedURLException;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Output;
import main.QException;
import main.Quic;


@SuppressWarnings("serial")
public class Pick extends JPanel implements ActionListener {
    static private final String newline = "\n";
    private JButton openButton;
    private JTextArea log;
    private JFileChooser fc;
    private FileNameExtensionFilter filter = new FileNameExtensionFilter(".xlsx", "xlsx");
    
    public Pick() {
        super(new BorderLayout());
 
        // Create the log first, because the action listeners need to refer to it.
        log = new JTextArea(20,35);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        log.setLineWrap(true);
        JScrollPane logScrollPane = new JScrollPane(log);
        PrintStream printStream = new PrintStream(new CustomOutputStream(log));
         
        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);
        
        String string =
            "<html><body height='' width='520'  style='padding-left:10px'>" +
            "<h1><center>Hi!</h1><p>1- Select your file by clicking on the button below.<br>" +
            "2- The file should be in xlsx format.<br>" +
            "3- The result of the analysis will be saved automatically.<br>" +
            "4- The process will take a while to complete.<br><br>";

        JLabel note = new JLabel(string);

        // Create a file chooser
        fc = new JFileChooser();
        
        // Create the open button.
        openButton = new JButton("Open");
        openButton.addActionListener(this);
 
        // For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); // Use FlowLayout
        buttonPanel.add(openButton);
        
        // Add the notes, buttons and the log to this panel.
        add(note, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
        
        System.out.println("Plaese select your file.");
    }

    /**
     * This class extends from OutputStream to redirect output to a JTextArrea
     */
    public class CustomOutputStream extends OutputStream {
        private JTextArea textArea;
         
        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }
         
        @Override
        public void write(int b) throws IOException {
            // Redirects data to the text area.
            textArea.append(String.valueOf((char)b));
            // Scrolls the text area to the end of data.
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
    
    public void actionPerformed(ActionEvent e) {
    	
    	final File file;
        // Handle open button action.
        if (e.getSource() == openButton) {
        	
        	fc.addChoosableFileFilter(filter);
        	fc.setAcceptAllFileFilterUsed(false);
        	
            int returnVal = fc.showOpenDialog(Pick.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                
                // This is where a real application would open the file.
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	try {
                    		// Calling the process from QUIC class.
							Quic.process(file.getName());
						} catch (QException e) {
							e.printStackTrace();
						}
                    }
                });
                
                // Shows the loading and times the progress.
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	log.setText(null);
                    	System.out.print("Loading");
                    	int count = 0;
                        while (thread1.isAlive()) {
                            try {
                            	System.out.print(".");
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            count++;
                        }
                        log.setText(null);
                        Output.printResult();
                    	System.out.println("It took " + count + " seconds to complete!" + newline);
                    }
                });
                
                thread1.start();
                thread2.start();
            }
            
            log.setCaretPosition(log.getDocument().getLength());

        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     * @throws MalformedURLException 
     */
    private static void createAndShowGUI(){
        // Create and set up the window.
        JFrame frame = new JFrame("QUIC UHIP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add content to the window.
        frame.add(new Pick());
 
        // Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
    
    /**
     * Starting point to trigger GUI.
     */
    public static void startGui() {
        // Schedule a job for the event dispatch thread:
        // Creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
				createAndShowGUI();
            }
        });
    }
}