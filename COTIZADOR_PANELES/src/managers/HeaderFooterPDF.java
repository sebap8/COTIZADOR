package managers;
import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPDF extends PdfPageEventHelper{
	 public void onStartPage(PdfWriter writer, Document document) {
		 	Image image = null;
			try {
				image = Image.getInstance("encabezado.png");//ECLIPSE
		        image.setAlignment(Element.ALIGN_CENTER);
		        image.setAbsolutePosition(20, 735);
		        image.scalePercent(24f, 24f);
		        writer.getDirectContent().addImage(image, true);
			} catch (BadElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    public void onEndPage(PdfWriter writer, Document document) {
	    	Image image = null;
	    	try {
				image = Image.getInstance("pie.png");//ECLIPSE
				image.setAlignment(Element.ALIGN_CENTER);
		        image.setAbsolutePosition(20, 10); //lado izquierdo y altura
		        image.scalePercent(24f, 24f);
				writer.getDirectContent().addImage(image, true);
			} catch (BadElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(DocumentException e){
				
			}
//	    	
//	        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("http://www.xxxx-your_example.com/"), 110, 30, 0);
//	        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Página " + document.getPageNumber()), 550, 30, 0);
	    }
}
