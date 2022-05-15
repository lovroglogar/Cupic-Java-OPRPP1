package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.oprpp1.hw08.jnotepadpp.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.SingleDocumentModel;
/**
 * Label koji je Multiple document listener, Single documentListener i Caret listener
 * @author Lovro Glogar
 *
 */
public class StatusBarLabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Duljina dokumenta koju status bar prikazuje
	 */
	private int length;
	
	/**
	 * Linija u kojoj se nalazimo u trenutnom dokumentu
	 */
	private int ln;
	
	/**
	 * Column u kojem se nalazimo u trenutnom dokumentu
	 */
	private int col;
	
	/**
	 * Broj odabranih znakova u dokumentu
	 */
	private int sel;
	
	/**
	 * Konstruktor
	 */
	public StatusBarLabel(MultipleDocumentModel mdm) {
		mdm.addMultipleDocumentListener(multipleDocumentListener);
		setValues(0, 0, 0, 0);
		this.setText("length:" + length +  "               Ln:" + ln + "  Col:" + col + "  Sel:" + sel);
	}

	/**
	 * Slusac za multiple document model, slusa za caret promjene trenutnog dokumenta, 
	 * kada se promjena dogodi, mice caret slusaca sa prijasnjeg JTextArea
	 * i postavlja ga da slusa za novi JTextArea
	 * 
	 */
	private MultipleDocumentListener multipleDocumentListener = new MultipleDocumentListener() {
		@Override
		public void documentRemoved(SingleDocumentModel model) {}
		
		@Override
		public void documentAdded(SingleDocumentModel model) {}
		
		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			if(previousModel != null) {
				JTextComponent previousComponent = previousModel.getTextComponent();
				
				previousComponent.removeCaretListener(caretListener);

			}
			if(currentModel != null) {
				JTextComponent currentComponent = currentModel.getTextComponent();
				
				currentComponent.addCaretListener(caretListener);
				length = currentComponent.getText().length();
				calculateLineAndColumn((JTextArea) currentComponent);
				calculateSelected((JTextArea) currentComponent);
				setTextWithValues();
			}
		}
	};
	
	/**
	 * Caret listener prati caret promjene u trenutnom dokumentu i mijenja vrijednosti status bar-a
	 */
	private CaretListener caretListener = new CaretListener() {
		@Override
		public void caretUpdate(CaretEvent e) {
			JTextArea component = (JTextArea) e.getSource();
			length = component.getText().length();
			calculateLineAndColumn(component);
			calculateSelected(component);
			setTextWithValues();
		}
	};
	
	/**
	 * Racuna koliko je znakova odabrano u dokumentu
	 * @param textArea
	 */
	private void calculateSelected(JTextArea textArea) {
		this.sel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
	}
	
	/**
	 * Racuna trenutnu liniju i column
	 * @param textArea
	 */
	private void calculateLineAndColumn(JTextArea textArea) {
		int caretPosition = textArea.getCaretPosition();
		Element root = textArea.getDocument().getDefaultRootElement();
		this.ln = root.getElementIndex(caretPosition) + 1;
		this.col = caretPosition - root.getElement(this.ln - 1).getStartOffset() + 1;
	}

	/**
	 * Postavlja tekst labele
	 */
	private void setTextWithValues() {
		this.setText("length:" + length +  "               Ln:" + ln + "  Col:" + col + "  Sel:" + sel);
	}

	/**
	 * Postavlja vrijednosti status bar-a
	 * @param length
	 * @param ln
	 * @param col
	 * @param sel
	 */
	private void setValues(int length, int ln, int col, int sel) {
		this.length = length;
		this.ln = ln;
		this.col = col;
		this.sel = sel;
		setTextWithValues();
	}
}
