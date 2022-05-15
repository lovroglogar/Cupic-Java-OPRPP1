package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Put do unmodifiedIcon ikone
	 */
	private static final Path UNMODIFIED_ICON_PATH = Path.of("icons/unmodifiedIcon.png");
	
	/**
	 * Put do modifiedIcon icone
	 */
	private static final Path MODIFIED_ICON_PATH = Path.of("icons/modifiedIcon.png");
	
	/**
	 * Dokumenti koji su otvorni
	 */
	private List<SingleDocumentModel> documents;
	
	/**
	 * Trenutni dokument
	 */
	private SingleDocumentModel current;
	
	/**
	 * Slusaci na dogadaje ove klase
	 */
	private List<MultipleDocumentListener> listeners;
	
	/**
	 * Localization provider
	 */
	private ILocalizationProvider localizationProvider;
	
	/**
	 * Slusac na single document model
	 */
	private SingleDocumentListener sdl = new SingleDocumentListener() {
		
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			int index = getIndexOfDocument(model);
			if(index == -1)
				return;
			ImageIcon icon;
			if(model.isModified())
				icon = readImageIcon(MODIFIED_ICON_PATH);
			else icon = readImageIcon(UNMODIFIED_ICON_PATH);
			setIconAt(index, icon);
		}
		
		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**
	 * Konstruktor
	 * @param lp
	 */
	public DefaultMultipleDocumentModel(ILocalizationProvider lp) {
		this.localizationProvider = lp;
		this.documents = new ArrayList<>();
		this.listeners = new ArrayList<>();
	}
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		Iterator<SingleDocumentModel> iterator = new Iterator<SingleDocumentModel>() {
			
			private int currentIndex = 0;
			
			@Override
			public SingleDocumentModel next() {
				SingleDocumentModel sdm = documents.get(currentIndex);
				currentIndex++;
				return sdm;
			}
			
			@Override
			public boolean hasNext() {
				return currentIndex < documents.size() && documents.get(currentIndex) != null;
			}
		};
		return iterator;
	}

	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel sdm = new DefaultSingleDocumentModel(null, "");
		addDocument(sdm);
		return sdm;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if(path == null)
			throw new NullPointerException("Path must not be null here");
		
		SingleDocumentModel temp;
		if((temp = this.findForPath(path)) != null) {
			this.setSelectedIndex(this.getIndexOfDocument(temp));
			return temp;
		}
		
		if(!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(
					this, 
					localizationProvider.getString("fileNotExist") + "\n" + path, 
					localizationProvider.getString("error"), 
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		String content;
		try(InputStream is = new BufferedInputStream(Files.newInputStream(path))){
			content = new String(is.readAllBytes(), "UTF-8");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, 
					 localizationProvider.getString("errorOpening") + "\n" +  path,
					localizationProvider.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		SingleDocumentModel sdm = new DefaultSingleDocumentModel(path, content);
		addDocument(sdm);
		return sdm;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(model == null)
			throw new NullPointerException("Model is null");
		
		Path pathToSave = null;
		if(newPath != null) {
			model.setFilePath(newPath);
			pathToSave = newPath;
		} else {
			pathToSave = model.getFilePath();
		} 
		
		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(pathToSave, podatci);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					this,
					localizationProvider.getString("errorWriting") + "\n" + pathToSave, 
					localizationProvider.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(
				this, 
				localizationProvider.getString("informationSaved"), 
				localizationProvider.getString("information"), 
				JOptionPane.INFORMATION_MESSAGE);
		
		model.setFilePath(pathToSave);
		model.setModified(false);
		this.setIconAt(getIndexOfDocument(model), readImageIcon(UNMODIFIED_ICON_PATH));
		this.setTitleAt(getIndexOfDocument(model), pathToSave.getFileName().toString());
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = getIndexOfDocument(model);
		this.remove(index);
		this.documents.remove(model);
		SingleDocumentModel newCurrent;
		if(documents.size() == 0)
			newCurrent = null;
		else newCurrent = getDocument(0);
		setCurrent(newCurrent);
		this.listeners.forEach(l -> l.documentRemoved(model));
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		if(!this.listeners.contains(l))
			this.listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return this.documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return this.documents.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		for(var sdm: this.documents) {
			Path p = sdm.getFilePath();
			if(p != null && p.toString().equals(path.toString()))
				return sdm;
		}
		return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		return this.documents.indexOf(doc);
	}
	
	/**
	 * Omota dani JTextArea u JPanel i JScrollPane i vraca taj JPanel
	 * @param ta
	 * @return
	 */
	private JPanel wrapTextArea(JTextArea ta) {
		JPanel p = new JPanel(new BorderLayout());
		JScrollPane sp = new JScrollPane(ta);
		p.add(sp, BorderLayout.CENTER);
		return p;
	}
	
	/**
	 * Cita i vraca ikonu
	 * @param p
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
	 * Dodaje dokument u TabbedPane
	 * @param sdm
	 */
	private void addToTab(SingleDocumentModel sdm) {
		String pathString;
		Path p = sdm.getFilePath();
		ImageIcon icon;
		if(p != null) 
			pathString = p.toString();
		else 
			pathString = "unnamed";
		sdm.setModified(false);
		icon = readImageIcon(UNMODIFIED_ICON_PATH);
		JPanel component = this.wrapTextArea(sdm.getTextComponent());
		this.addTab(Path.of(pathString).getFileName().toString(), icon, component, pathString);
		this.setSelectedIndex(this.getNumberOfDocuments() - 1);
	}
	
	@Override
	public void setSelectedIndex(int index) {
		setCurrent(this.getDocument(index));
		super.setSelectedIndex(index);
	}
	
	private void addDocument(SingleDocumentModel sdm) {
		this.documents.add(sdm);
		addToTab(sdm);
		this.setCurrent(sdm);
		sdm.addSingleDocumentListener(sdl);
		this.listeners.forEach(l -> l.documentAdded(sdm));
	}
	
	private void setCurrent(SingleDocumentModel sdm) {
		SingleDocumentModel previous = current;
		this.current = sdm;
		for(MultipleDocumentListener mdl: listeners) {
			mdl.currentDocumentChanged(previous, current);
		}
	}
	
}
