package managers;

import java.awt.font.ImageGraphicAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.ListItem;

import beans.ItemPresupuestoBean;
import beans.PresupuestoBean;
import controlador.Controlador; 

/**
 * Example of using the iText library to work with PDF documents on Java, 
 * lets you create, analyze, modify and maintain documents in this format.
 * Ejemplo de uso de la librerÃ­a iText para trabajar con documentos PDF en Java, 
 * nos permite crear, analizar, modificar y mantener documentos en este formato.
 *
 * @author xules You can follow me on my website http://www.codigoxules.org/en
 * Puedes seguirme en mi web http://www.codigoxules.org
 */
public class PDFManagerAUX {
    // Fonts definitions (DefiniciÃ³n de fuentes).
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLD);
    
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
    private static final Font letraPresupuesto = FontFactory.getFont("CenturyGothic",12,Font.NORMAL);
    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);    
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    
    private static final Font fechaClienteBold= new Font(FontFactory.getFont("CenturyGothic",12,Font.BOLD));
    private static final Font fechaCliente= new Font(FontFactory.getFont("CenturyGothic",12,Font.NORMAL));
    
    private static final String iTextExampleImage = "img/1a.png";
    private static final String productoSinFoto = "img/productoSinImagen.png";
    private BaseColor colorImpar = WebColors.getRGBColor("#BBD2EE");
    private BaseColor colorPar = WebColors.getRGBColor("#D8DADC");
    private String separador="______________________________________________________________________________";
    
    /**
     * We create a PDF document with iText using different elements to learn 
     * to use this library.
     * Creamos un documento PDF con iText usando diferentes elementos para aprender 
     * a usar esta librerÃ­a.
     * @param pdfNewFile  <code>String</code> 
     *      pdf File we are going to write. 
     *      Fichero pdf en el que vamos a escribir. 
     */
    public void createPDF(File pdfNewFile, PresupuestoBean p) {
        // We create the document and set the file name.        
        // Creamos el documento e indicamos el nombre del fichero.
        try {
            Document document = new Document();
            try {
                PdfWriter escritor=PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
                HeaderFooterPDF event = new HeaderFooterPDF();
                escritor.setPageEvent(event);
                Document    documento       =   new Document(PageSize.A4);
                documento.setMargins(60, 60, 60, 60); // (izq, der, arriba, abajo)
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF "
                        + "(No se encontrÃ³ el fichero para generar el pdf)" + fileNotFoundException);
            }
            
            document.open();
            
            // We add metadata to PDF
            // AÃ±adimos los metadatos del PDF
            document.addTitle("PRESUPUESTO");
            document.addSubject("PRESUPUESTO");
            document.addKeywords("AP");
            document.addAuthor("AISLACIONES PATAGÓNICAS");
            document.addCreator("AISLACIONES PATAGÓNICAS");
            
            String codigoListaPrecio=Controlador.getInstancia().obtenerCodigoListaPrecio(p.getListaPrecio());

           
//            Paragraph p1 = new Paragraph(); 
//            p1.setAlignment(Element.ALIGN_CENTER); //PARRAFO ALINEADO AL CENTRO
            
            Chapter chapter2 = new Chapter("", 1);
//            chapter2.setNumberDepth(0);
            chapter2.add(new Paragraph(" ", paragraphFont));
            chapter2.add(new Paragraph(" ", paragraphFont));
            chapter2.add(new Paragraph(" ", paragraphFont));
            chapter2.add(new Paragraph(" ", paragraphFont));
            Paragraph numero=new Paragraph("PRESUPUESTO "+codigoListaPrecio+" - "+String.format("%08d", p.getNumero()), categoryFont);
            numero.setAlignment(Element.ALIGN_RIGHT);
            chapter2.add(numero);
            chapter2.add(new Paragraph(separador,letraPresupuesto));
            chapter2.add(new Paragraph(" ", paragraphFont));
//            chapter2.add(new Paragraph("Fecha: "+p.getFecha(), letraPresupuesto));
            
          
            PdfPCell columnHeader;
            //AGREGADO ALINEADO FECHA
            PdfPTable table1 = new PdfPTable(2);
            table1.setWidthPercentage(100);
            float[] columnWidths1 = new float[]{15f,80f};
            table1.setWidths(columnWidths1);
            columnHeader = new PdfPCell(new Phrase("Fecha ",fechaCliente));
        	columnHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
        	columnHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	columnHeader.setBorder(Rectangle.NO_BORDER);
        	table1.addCell(columnHeader);
        	
        	columnHeader = new PdfPCell(new Phrase(p.getFecha(),fechaClienteBold));
         	columnHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
         	columnHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	columnHeader.setBorder(Rectangle.NO_BORDER);
         	table1.addCell(columnHeader);
         	
         	columnHeader = new PdfPCell(new Phrase("Cliente ",fechaCliente));
        	columnHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
        	columnHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	columnHeader.setBorder(Rectangle.NO_BORDER);
        	table1.addCell(columnHeader);
        	
        	columnHeader = new PdfPCell(new Phrase(p.getCliente(),fechaClienteBold));
         	columnHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
         	columnHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	columnHeader.setBorder(Rectangle.NO_BORDER);
         	table1.addCell(columnHeader);
         	
         	columnHeader = new PdfPCell(new Phrase("Ubicación ",fechaCliente));
        	columnHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
        	columnHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	columnHeader.setBorder(Rectangle.NO_BORDER);
        	table1.addCell(columnHeader);
        	
        	columnHeader = new PdfPCell(new Phrase(Controlador.getInstancia().obtenerUbicacionCliente(p.getCliente()),fechaClienteBold));
         	columnHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
         	columnHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	columnHeader.setBorder(Rectangle.NO_BORDER);
         	table1.addCell(columnHeader);
        	
            chapter2.add(table1);
            //FIN AGREGADO ALINEADO FECHA
//            chapter2.add(new Paragraph("Cliente: "+p.getCliente(), letraPresupuesto));
//            chapter2.add(new Paragraph("Ubicación: "+Controlador.getInstancia().obtenerUbicacionCliente(p.getCliente()), letraPresupuesto));
            chapter2.add(new Paragraph(separador,letraPresupuesto));
            chapter2.add(new Paragraph(" ", paragraphFont));
            chapter2.add(new Paragraph(" ", paragraphFont));
            
            Paragraph p2 = new Paragraph("ITEMS PRESUPUESTADOS", paragraphFont); 
            p2.setAlignment(Element.ALIGN_CENTER); 
            chapter2.add(p2);
            chapter2.add(new Paragraph(" ", paragraphFont));
            int numColumns=3;
            PdfPTable table = new PdfPTable(numColumns);
            float[] columnWidths = new float[]{30f, 70f, 30f};
            table.setWidths(columnWidths);
            table.setWidthPercentage(100);
            
            Vector<ItemPresupuestoBean> items= p.getItemsVector();
            BaseColor colorAux;
            for (int i = 0; i < items.size() ; i++) {
            	ItemPresupuestoBean it=items.get(i);
            	if(i%2==0) {
            		colorAux=colorPar;
            	}else {
            		colorAux=colorImpar;
            	}

            	byte[] result=Controlador.getInstancia().obtenerImagen(it.getProducto());
            	Image iTextImage = null;
            	if(result!=null) {
            		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(result));
       			 	ByteArrayOutputStream baos = new ByteArrayOutputStream();
       			 	ImageIO.write(bufferedImage, "png", baos);
       			 	iTextImage = Image.getInstance(baos.toByteArray());
            	}else {
            		iTextImage= Image.getInstance(productoSinFoto);
            	}
   			 	
   			 	iTextImage.scaleAbsolute(40f, 40f);
   			 	columnHeader =new PdfPCell(iTextImage);
            	columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            	columnHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	columnHeader.setBorder(Rectangle.NO_BORDER);
            	columnHeader.setBackgroundColor(colorAux);
            	table.addCell(columnHeader);
            	
            	columnHeader = new PdfPCell(new Phrase(it.getProducto()));
            	columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            	columnHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	columnHeader.setBorder(Rectangle.NO_BORDER);
            	columnHeader.setBackgroundColor(colorAux);
            	table.addCell(columnHeader);
            	
            	columnHeader = new PdfPCell(new Phrase(String.valueOf(it.getCantidad())));
            	columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            	columnHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
            	columnHeader.setBorder(Rectangle.NO_BORDER);
            	columnHeader.setBackgroundColor(colorAux);
            	table.addCell(columnHeader);
            	

            }
            chapter2.add(table);
            
            chapter2.add(new Paragraph(" ", paragraphFont));
            chapter2.add(new Paragraph(" ", paragraphFont));
            chapter2.add(new Paragraph(" ", paragraphFont));
            float auxBonificacion=0;
            if(p.getDescuento()>0) {
            	chapter2.add(new Paragraph("Subtotal:  "+formatearImporte(p.getSubtotal())));
            	auxBonificacion=Float.valueOf(p.getDescuento())/100;
            	auxBonificacion=auxBonificacion*p.getSubtotal();
            	chapter2.add(new Paragraph("BonificaciÃ³n:  "+formatearImporte(auxBonificacion)+" ( "+p.getDescuento()+"% )"));
            	chapter2.add(new Paragraph(" ", paragraphFont));
            }else {
            	chapter2.add(new Paragraph("Subtotal:  "+formatearImporte(p.getSubtotal())));
            	chapter2.add(new Paragraph(" ", paragraphFont));
            }
            chapter2.add(new Paragraph(" ", paragraphFont));
            Paragraph p4 = new Paragraph("Precio: "+formatearImporte((p.getSubtotal()-auxBonificacion))+" + IVA"); 
            p4.setAlignment(Element.ALIGN_CENTER); //PARRAFO ALINEADO AL CENTRO
            chapter2.add(p4);
            chapter2.add(new Paragraph(" ", paragraphFont));
            chapter2.add(new Paragraph(" ", paragraphFont));
            chapter2.add(new Paragraph("Validéz:  "+p.getValidez()+" DÍAS (Vencimiento: "+p.getFechaVencimiento()+" )") );
            chapter2.add(new Paragraph("Condición de pago:  "+p.getFormaDePago(), paragraphFont));
            
            document.add(chapter2);
            document.close();
        } catch (DocumentException documentException) {
            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	private String formatearDescuento(int descuento) {
		DecimalFormat df = new DecimalFormat("#");
		return df.format(descuento);
	}
	private String presentarFecha(String fecha) {
		char[] result = {'a','a','/','a','a','/','a','a','a','a'};
		fecha.getChars(6, 7, result, 0);
		fecha.getChars(7, 8, result, 1);
		fecha.getChars(5, 6, result, 4);
		fecha.getChars(4, 5, result, 3);
		fecha.getChars(0, 1, result, 6);
		fecha.getChars(1, 2, result, 7);
		fecha.getChars(2, 3, result, 8);
		fecha.getChars(3, 4, result, 9);
		return String.valueOf(result);
	}
	private String formatearImporte(float precio) {
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(precio);
	}

}
