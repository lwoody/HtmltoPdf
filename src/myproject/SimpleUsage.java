package myproject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder.TextDirection;

public class SimpleUsage 
{
	public static void main(String[] args)
	{
		new SimpleUsage().exportToPdfBox("file:///Users/LEE/Desktop/test.html", "/Users/LEE/Desktop/output.pdf");
	}
	
	public void exportToPdfBox(String url, String out)
	{
            OutputStream os = null;
       
              try {
               os = new FileOutputStream(out);
       
               try {
                     // There are more options on the builder than shown below.
                     PdfRendererBuilder builder = new PdfRendererBuilder();

                     builder.withUri(url);
                     builder.toStream(os);
                     builder.run();
                     
               } catch (Exception e) {
                     e.printStackTrace();
                     // LOG exception
               } finally {
                     try {
                            os.close();
                     } catch (IOException e) {
                            // swallow
                     }
               }
              }
              catch (IOException e1) {
                     e1.printStackTrace();
                     // LOG exception.
              }
	}
}