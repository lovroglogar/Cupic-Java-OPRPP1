package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.function.UnaryOperator;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.oprpp1.hw08.jnotepadpp.components.JLabelClockListener;
import hr.fer.oprpp1.hw08.jnotepadpp.components.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.components.LMenuItemCaretListener;
import hr.fer.oprpp1.hw08.jnotepadpp.components.StatusBarLabel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LanguageAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * NotepadPP
 * @author Lovro Glogar
 *
 */
public class JNotepadPP extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * MultipleDocumentModel referenca
	 */
	private MultipleDocumentModel mdm;
	
	/**
	 * Localization provider
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * File chooser
	 */
	private JFileChooser fc;
	
	/**
	 * Sat
	 */
	private Clock clock;
	
	/**
	 * Slusatelj zatvaranja prozora
	 */
	private WindowListener windowListener = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			JNotepadPP.this.checkStatusAndExit();
		};
	};
	
	/**
	 * Konstruktor
	 */
	public JNotepadPP() {
		this.flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		this.fc = new JFileChooser();
		this.clock = new Clock();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(flp);
		this.addWindowListener(windowListener);
		setLocation(0, 0);
		setSize(900, 600);
		initActions();
		initGUI();
	}

	/**
	 * Akcija hrvtskog jezika
	 */
	private Action hrLanguageAction = new LanguageAction("hr");
	
	/**
	 * Akcija engleskog jezika
	 */
	private Action enLanguageAction = new LanguageAction("en");
	
	/**
	 * Akcija njemackog jezika
	 */
	private Action deLanguageAction = new LanguageAction("de");

	/**
	 * Akcija otvaranja dokumenta
	 */
	private Action openDocumentAction;
	
	/**
	 * Akcija otvaranja novog dokumenta
	 */
	private Action newDocumentAction;
	
	/**
	 * Akcija spremanja dokumnta kao
	 */
	private Action saveAsDocumentAction;
	
	/**
	 * Akcija spremanja dokumenta
	 */
	private Action saveDocumentAction;
	
	/**
	 * Akcija zatvaranja dokumenta
	 */
	private Action closeCurrentDocumentAction;
	
	/**
	 * Akcija gasenja aplikacije
	 */
	private Action exitAction;
	
	/**
	 * Akcija statistike dokumenta
	 */
	private Action statisticalInfoAction;
	
	/**
	 * Akcija rezanja 
	 */
	private Action cutTextAction;
	
	/**
	 * Akcija kopiranja
	 */
	private Action copyTextAction;
	
	/**
	 * Akcija ljepljenja
	 */
	private Action pasteTextAction;
	
	/**
	 * Akcija pretvaranja tekstu u velika slova
	 */
	private Action uppercaseTextAction;
	
	/**
	 * Akcija pretvaranja tekstu u mala slova
	 */
	private Action lowercaseTextAction;
	
	/**
	 * Akcija invertiranja teksta
	 */
	private Action invertTextAction;
	
	/**
	 * Akcija sortiranja odabranih linija uzlazno
	 */
	private Action ascendingAction;

	/**
	 * Akcija sortiranja odabranih linija silazno
	 */
	private Action descendingAction;
	
	/**
	 * Akcija brisanja duplih linija medu odabranim
	 */
	private Action uniqueAction;
	
	/**
	 * Inicijalizacija akcija
	 */
	private void initActions() {
		//Akcija otvaranja
		openDocumentAction = new LocalizableAction("open", flp) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fc.setDialogTitle(flp.getString("openFile"));
				if(fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File fileName = fc.getSelectedFile();
				Path filePath = fileName.toPath();
				
				JNotepadPP.this.mdm.loadDocument(filePath);
			}
		};
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		
		//Akcija nove datoteke
		newDocumentAction = new LocalizableAction("newFile", flp) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				JNotepadPP.this.mdm.createNewDocument();
			}
		};
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		
		//Akcija spremi
		saveDocumentAction = new LocalizableAction("save", flp) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel current = mdm.getCurrentDocument();
				if(current == null)
					return;
				if(current.getFilePath() == null) {
					saveAsDocumentAction.actionPerformed(e);
					return;
				}
				
				JNotepadPP.this.mdm.saveDocument(current, null);;
			}
		};
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		
		//Akcija spremi kao
		saveAsDocumentAction = new LocalizableAction("saveAs", flp) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Path filePath = choosePathForSavingAs();
				
				if(filePath == null)
					return;
				
				JNotepadPP.this.mdm.saveDocument(mdm.getCurrentDocument(), filePath);;
			}
		};		
		
		//Akcija zatvaranja dokumenta
		closeCurrentDocumentAction = new LocalizableAction("close", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel current = mdm.getCurrentDocument();
				if(current == null)
					return;
				if(current.isModified()) {
					int choice = choiceForClosing(current);
					if(choice == -1 || choice == 2)
						return;
					else if(choice == 0)
						saveDocumentAction.actionPerformed(e);
				}
				
				mdm.closeDocument(current);
			}
		};
		closeCurrentDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		
		//Akcija zatvaranja programa
		exitAction = new LocalizableAction("exit", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				checkStatusAndExit();
			}
		};
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		
		//Akcija statistike
		statisticalInfoAction = new LocalizableAction("statisticalInfo", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel current = mdm.getCurrentDocument();
				if(current == null)
					return;
				JTextArea ts = current.getTextComponent();
				
				String text = ts.getText();
				
				int numberOfChars = text.length();
				int numberOfNBCharacters = 0;
				for(int i = 0; i < text.length(); i++) {
					if(!Character.isWhitespace(text.charAt(i)))
						numberOfNBCharacters++;
				}
				String[] lines = text.split("\n|\r|\n\r");
				int numberOfLines = lines.length;
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						flp.getString("statInfoDoc") + ":\n"
						 + flp.getString("numOfChars") + ": " + numberOfChars + "\n"
						 + flp.getString("numOfNBChars") + ": " + numberOfNBCharacters + "\n"
						 + flp.getString("numOfLines") + ": " + numberOfLines,
						flp.getString("information"),
						JOptionPane.INFORMATION_MESSAGE);
			}
		};
		statisticalInfoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		
		//Akcija cut
		cutTextAction = new DefaultEditorKit.CutAction();
		cutTextAction.putValue(Action.NAME, flp.getString("cut"));
		cutTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		
		//Akcija copy
		copyTextAction = new DefaultEditorKit.CopyAction();
		copyTextAction.putValue(Action.NAME, flp.getString("copy"));
		copyTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
				
		//Akcija paste
		pasteTextAction = new DefaultEditorKit.PasteAction();
		pasteTextAction.putValue(Action.NAME, flp.getString("paste"));
		pasteTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
	
		//Akcija uppercase
		uppercaseTextAction = new LocalizableAction("uppercase", flp) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = mdm.getCurrentDocument().getTextComponent();
				Caret caret = textArea.getCaret();
				String text = textArea.getText();
				int dot = caret.getDot();
				int mark = caret.getMark();
				int offset = dot - mark;
				StringBuilder sb = new StringBuilder();
				if(offset < 0) {
					sb.append(text.substring(0, dot ));
					sb.append(text.substring(dot, mark).toUpperCase());
					sb.append(text.substring(mark));
				}else {
					sb.append(text.substring(0, mark));
					sb.append(text.substring(mark, dot).toUpperCase());
					sb.append(text.substring(dot));
				}
				textArea.setText(sb.toString());
			}
		};
		uppercaseTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
	
		//Akcija lowercase
		lowercaseTextAction = new LocalizableAction("uppercase", flp) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = mdm.getCurrentDocument().getTextComponent();
				Caret caret = textArea.getCaret();
				String text = textArea.getText();
				int dot = caret.getDot();
				int mark = caret.getMark();
				int offset = dot - mark;
				StringBuilder sb = new StringBuilder();
				if(offset < 0) {
					sb.append(text.substring(0, dot ));
					sb.append(text.substring(dot, mark).toLowerCase());
					sb.append(text.substring(mark));
				}else {
					sb.append(text.substring(0, mark));
					sb.append(text.substring(mark, dot).toLowerCase());
					sb.append(text.substring(dot));
				}
				textArea.setText(sb.toString());
			}
		};
		lowercaseTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));	
	
		//Akcija invert case
		invertTextAction = new LocalizableAction("invertcase", flp) {
			private static final long serialVersionUID = 1L;
			
			private String invertCase(String text) {
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < text.length(); i++) {
					if(Character.isUpperCase(text.charAt(i)))
						sb.append(Character.toLowerCase(text.charAt(i)));
					else
						sb.append(Character.toUpperCase(text.charAt(i)));
				}
				return sb.toString();
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = mdm.getCurrentDocument().getTextComponent();
				Caret caret = textArea.getCaret();
				String text = textArea.getText();
				int dot = caret.getDot();
				int mark = caret.getMark();
				int offset = dot - mark;
				StringBuilder sb = new StringBuilder();
				if(offset < 0) {
					sb.append(text.substring(0, dot ));
					sb.append(invertCase(text.substring(dot, mark)));
					sb.append(text.substring(mark));
					
				}else {
					sb.append(text.substring(0, mark));
					sb.append(invertCase(text.substring(mark, dot)));
					sb.append(text.substring(dot));
				}
				textArea.setText(sb.toString());
			}
		};
		invertTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));	
	
		//Akcija ascending sort
		ascendingAction = new LocalizableAction("ascending", flp) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				doOperationOnSelectedLines(mdm.getCurrentDocument().getTextComponent(), (lines) -> {
					Locale locale = new Locale(flp.getLanguage());
					Collator collator = Collator.getInstance(locale);
					Collections.sort(Arrays.asList(lines), collator);
					return lines;
				});
			}
		};
		ascendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));	
		
		//Akcija descending sort
		descendingAction = new LocalizableAction("descending", flp) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				doOperationOnSelectedLines(mdm.getCurrentDocument().getTextComponent(), (lines) -> {
					Locale locale = new Locale(flp.getLanguage());
					Collator collator = Collator.getInstance(locale);
					Collections.sort(Arrays.asList(lines), collator.reversed());
					return lines;
				});
			}
		};
		descendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift D"));
		
		//Akcija unique
		uniqueAction = new LocalizableAction("unique", flp) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				doOperationOnSelectedLines(mdm.getCurrentDocument().getTextComponent(), (lines) -> {
					List<String> returnList = new ArrayList<>(new LinkedHashSet<>(Arrays.asList(lines)));
					String[] returnArray = new String[returnList.size()];
					return returnList.toArray(returnArray);
				});
			}
		};
		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift U"));
	}
	
	/**
	 * Incijalizacija GUI-a
	 */
	private void initGUI() {
		mdm = new DefaultMultipleDocumentModel(flp);
		this.setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();
		
		// File menu
		LJMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeCurrentDocumentAction));
		fileMenu.add(new JMenuItem(statisticalInfoAction));
		fileMenu.add(new JMenuItem(exitAction));
		
		// Edit menu
		LJMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(cutTextAction));
		editMenu.add(new JMenuItem(copyTextAction));
		editMenu.add(new JMenuItem(pasteTextAction));
		
		//Tools menu
		LJMenu toolsMenu = new LJMenu("tools", flp);
		menuBar.add(toolsMenu);
		
		LJMenu changeCaseMenu = new LJMenu("changeCase", flp);
		toolsMenu.add(changeCaseMenu);
		
		LMenuItemCaretListener uppercaseMenu = new LMenuItemCaretListener("uppercase", flp, uppercaseTextAction);
		mdm.addMultipleDocumentListener(uppercaseMenu);
		changeCaseMenu.add(uppercaseMenu);
		LMenuItemCaretListener lowercaseMenu = new LMenuItemCaretListener("lowercase", flp, lowercaseTextAction);
		mdm.addMultipleDocumentListener(lowercaseMenu);
		changeCaseMenu.add(lowercaseMenu);
		LMenuItemCaretListener invertcaseMenu = new LMenuItemCaretListener("invertcase", flp, invertTextAction);
		mdm.addMultipleDocumentListener(invertcaseMenu);
		changeCaseMenu.add(invertcaseMenu);
		
		LJMenu sortMenu = new LJMenu("sort", flp);
		toolsMenu.add(sortMenu);
		
		LMenuItemCaretListener ascendingMenu = new LMenuItemCaretListener("ascending", flp, ascendingAction);
		mdm.addMultipleDocumentListener(ascendingMenu);
		sortMenu.add(ascendingMenu);
		LMenuItemCaretListener descendingMenu = new LMenuItemCaretListener("descending", flp, descendingAction);
		mdm.addMultipleDocumentListener(descendingMenu);
		sortMenu.add(descendingMenu);
		
		LMenuItemCaretListener uniqueMenu = new LMenuItemCaretListener("unique", flp, uniqueAction);
		mdm.addMultipleDocumentListener(uniqueMenu);
		toolsMenu.add(uniqueMenu);
		
		// Language menu
		LJMenu languageMenu = new LJMenu("language", flp);
		menuBar.add(languageMenu);
		
		languageMenu.add(new JMenuItem(hrLanguageAction));
		languageMenu.add(new JMenuItem(enLanguageAction));
		languageMenu.add(new JMenuItem(deLanguageAction));
		
		this.setJMenuBar(menuBar);
		
		// Toolbar
		JToolBar toolBar = new JToolBar();
		
		toolBar.setRollover(true);
		
		newDocumentAction.putValue(Action.SMALL_ICON, readImageIcon(Path.of("icons/newDocument.png")));
		openDocumentAction.putValue(Action.SMALL_ICON, readImageIcon(Path.of("icons/openDocumentIcon.png")));
		saveDocumentAction.putValue(Action.SMALL_ICON, readImageIcon(Path.of("icons/saveDocumentIcon.png")));
		closeCurrentDocumentAction.putValue(Action.SMALL_ICON, readImageIcon(Path.of("icons/closeDocumentIcon.png")));
		cutTextAction.putValue(Action.SMALL_ICON, readImageIcon(Path.of("icons/cutTextIcon.png")));
		copyTextAction.putValue(Action.SMALL_ICON, readImageIcon(Path.of("icons/copyTextIcon.png")));
		pasteTextAction.putValue(Action.SMALL_ICON, readImageIcon(Path.of("icons/pasteTextIcon.png")));
		statisticalInfoAction.putValue(Action.SMALL_ICON, readImageIcon(Path.of("icons/statisticalInfoIcon.png")));
		exitAction.putValue(Action.SMALL_ICON, readImageIcon(Path.of("icons/exitIcon.png")));

		
		toolBar.add(newDocumentAction);
		toolBar.addSeparator();
		toolBar.add(openDocumentAction);
		toolBar.addSeparator();
		toolBar.add(saveDocumentAction);
		toolBar.addSeparator();
		toolBar.add(closeCurrentDocumentAction);
		toolBar.addSeparator();
		toolBar.add(cutTextAction);
		toolBar.addSeparator();
		toolBar.add(copyTextAction);
		toolBar.addSeparator();
		toolBar.add(pasteTextAction);
		toolBar.addSeparator();
		toolBar.add(statisticalInfoAction);
		toolBar.addSeparator();
		toolBar.add(exitAction);
		
		this.add(toolBar, BorderLayout.NORTH);
		
		// Tab and status panel
		JPanel tabAndStatusPanel = new JPanel(new BorderLayout());
		tabAndStatusPanel.add((DefaultMultipleDocumentModel) mdm, BorderLayout.CENTER);
		
		// Status bar
		JPanel statusBarPanel = new JPanel(new BorderLayout());
		statusBarPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		StatusBarLabel statusLabel = new StatusBarLabel(mdm);

		JLabelClockListener timeLabel = new JLabelClockListener();
		timeLabel.setText(clock.getTime());
		clock.addClockListener(timeLabel);
		
		statusBarPanel.add(statusLabel, BorderLayout.CENTER);
		statusBarPanel.add(timeLabel, BorderLayout.LINE_END);
		tabAndStatusPanel.add(statusBarPanel, BorderLayout.AFTER_LAST_LINE);
		this.add(tabAndStatusPanel, BorderLayout.CENTER);
	}

	/**
	 * Provjeri modified status svih otvorenih dokumenata i pokusaj spremniti prije zatvranja aplikacije
	 */
	public void checkStatusAndExit() {
		Iterator<SingleDocumentModel> it = mdm.iterator();
		while(it.hasNext()) {
			SingleDocumentModel sdm = it.next();
			int choice = choiceForClosing(sdm);
			if(choice == -1 || choice == 2)
				return;
			if(choice == 0) {
				Path filePath;
				if(sdm.getFilePath() == null) {
					filePath = choosePathForSavingAs();
					if(filePath == null)
						return;
				} else
					filePath = sdm.getFilePath();
				
				JNotepadPP.this.mdm.saveDocument(sdm, filePath);
			}
		}
		this.clock.setStopFlag(true);
		this.dispose();
	}
	
	/**
	 * Trazi od korisnika informaciju treba li spremiti dokument prije zatvaranja
	 * @param sdm dokument koji se zatvara
	 * @return
	 */
	private int choiceForClosing(SingleDocumentModel sdm) {
		if(sdm == null)
			return -1;
		if(sdm.isModified()) {
			Object[] options = {"Yes", "No", "Cancel"};
			int choice = JOptionPane.showOptionDialog(
					JNotepadPP.this, 
					flp.getString("saveQuestionBeforeCloseQuestion") + "\n" + (sdm.getFilePath() == null ? "unnamed" : sdm.getFilePath().getFileName()), 
					flp.getString("warning"), 
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.QUESTION_MESSAGE, 
					null, 
					options, 
					options[2]);
			return choice;
		}
		return 1;
	}
	
	/**
	 * Trazi od korisnika koji put da se koristi za spremanje kao
	 * @return
	 */
	private Path choosePathForSavingAs() {
		Path filePath;
		fc.setDialogTitle(flp.getString("saveAs"));
		if(fc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(JNotepadPP.this,
					flp.getString("saveWarning"),
					flp.getString("warning"),
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		filePath = fc.getSelectedFile().toPath();
		
		if(filePath.toFile().exists()) {
			fc.setDialogTitle("Warning");
			Object[] options = {flp.getString("yes"), flp.getString("no")};
			int choice2 = JOptionPane.showOptionDialog(
					JNotepadPP.this, 
					flp.getString("fileExistsOverwriteQuestion"), 
					flp.getString("warning"), 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, 
					null, 
					options, 
					options[1]);
			if(choice2 == 1)
				return null;
		}
		return filePath;
	}
	
	/**
	 * Cita ikonu
	 * @param p put ikone
	 * @return
	 */
	private ImageIcon readImageIcon(Path p) {
		try(InputStream is = this.getClass().getResourceAsStream(p.toString());) {
			if(is == null)
				throw new NullPointerException("Loading image failed");
			byte[] bytes = is.readAllBytes();
			return new ImageIcon(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Radi operaciju definiranu operatorom nad odabranim linijama text component-a
	 * @param textComponent
	 * @param operator
	 */
	private void doOperationOnSelectedLines(JTextComponent textComponent, UnaryOperator<String[]> operator) {
		int dot = textComponent.getCaretPosition();
		int mark = textComponent.getCaret().getMark();
		
		Element root = textComponent.getDocument().getDefaultRootElement();
		
		String[] lines = textComponent.getText().split("\n");
		int dotLine = root.getElementIndex(dot);
		int markLine = root.getElementIndex(mark);
		
		//Find selected lines
		int len = Math.abs(markLine - dotLine) + 1;
		String[] selectedLines = new String[len];
		int start, end;
		
		if(markLine < dotLine) {
			start = markLine;
			end = dotLine;
		} else {
			start = dotLine;
			end = markLine;
		}
		
		for(int i = start; i <= end; i++) 
			selectedLines[i - start] = lines[i];
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < start; i++)
			sb.append(lines[i] + "\n");
		
		String[] processedSelectedLines = operator.apply(selectedLines);
		for(int i = 0; i < processedSelectedLines.length; i++)
			sb.append(processedSelectedLines[i] + "\n");
		
		for(int i = end + 1; i < lines.length; i++)
			sb.append(lines[i] + "\n");
		
		textComponent.setText(sb.toString());
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
	
	/**
	 * Sat
	 * @author Lovro Glogar
	 *
	 */
	private class Clock {
		List<ClockListener> listeners;
		
		String time;
		
		volatile boolean stopFlag;
		
		public Clock() {
			this.listeners = new ArrayList<>();
			this.stopFlag = false;
			Thread t = new Thread(()->{
				while(true) {
					try {
						if(stopFlag)
							return;
						Thread.sleep(500);
					} catch(Exception ex) {}
					SwingUtilities.invokeLater(()->{
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
						time = formatter.format(LocalDateTime.now());
						listeners.forEach(l -> l.clockChanged(time));
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}
		
		public void addClockListener(ClockListener l) {
			this.listeners.add(l);
		}
		
		public String getTime() {
			return this.time;
		}
		
		public void setStopFlag(boolean b) {
			this.stopFlag = b;
		}
	}
}
