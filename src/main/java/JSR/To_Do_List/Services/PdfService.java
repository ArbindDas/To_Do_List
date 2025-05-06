
package JSR.To_Do_List.Services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import JSR.To_Do_List.Model.Customer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public byte[] generatePdf(Customer customer) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Customer Details");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("ID: " + customer.getId());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("First Name: " + customer.getFirstName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Last Name: " + customer.getLastName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Email: " + customer.getEmail());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Phone: " + customer.getPhone());
                contentStream.endText();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }
}
