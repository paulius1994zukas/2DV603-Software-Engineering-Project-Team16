package hotel.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import hotel.database.DbConnect;
import hotel.models.Fee;
import hotel.models.Reservation;
import hotel.models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InvoicePrinter {

    ObservableList<Room> roomInfo = FXCollections.observableArrayList();
    ObservableList<Fee> fees = FXCollections.observableArrayList();
    Reservation reservation;
    private AlertMaker alert = new AlertMaker();
    private String maxRate = "";
    private int billRowIndex = 1;
    private int feeRowIndex = 1;
    private int totalFeesCounter = 0;

    public boolean createPDF(String pdfFilename) {
        try {
            reservation = Reservation.getReservation();
            LocalDate localDate = LocalDate.now();
            OutputStream file = new FileOutputStream(new File(pdfFilename));
            Document document = new Document();
            PdfWriter.getInstance(document, file);

            Image image = Image.getInstance("src/resources/images/invoiceLogo.png");//Header Image
            image.scaleAbsolute(540f, 72f);//image width,height

            PdfPTable irdTable = new PdfPTable(2);
            irdTable.addCell(getIRDCell("Invoice No"));
            irdTable.addCell(getIRDCell("Invoice Date"));
            if (reservation.getLocation() == "Kalmar")
                irdTable.addCell(getIRDCell(String.format("KLM%s", reservation.getId())));
            else
                irdTable.addCell(getIRDCell(String.format("VXO%s", reservation.getId())));
            irdTable.addCell(getIRDCell(localDate.toString()));

            PdfPTable irhTable = new PdfPTable(3);
            irhTable.setWidthPercentage(100);

            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("Invoice", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            PdfPCell invoiceTable = new PdfPCell(irdTable);
            invoiceTable.setBorder(0);
            irhTable.addCell(invoiceTable);

            FontSelector fs = new FontSelector();
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD);
            fs.addFont(font);
            Phrase bill = fs.process("Bill To"); // customer information
            Paragraph name = new Paragraph(String.format("Mr/Ms.%s %s", reservation.getFirstName(), reservation.getLastName()));
            name.setIndentationLeft(20);
            Paragraph contact = new Paragraph(String.format("Phone number: %s", reservation.getPhoneNumber()));
            contact.setIndentationLeft(20);
            Paragraph address = new Paragraph(String.format(reservation.getAddress()));
            address.setIndentationLeft(20);

            PdfPTable billTable = new PdfPTable(7); //one page contains 15 records
            billTable.setWidthPercentage(100);
            billTable.setWidths(new float[]{1, 2, 4, 2, 2, 2, 1});
            billTable.setSpacingBefore(30.0f);
            billTable.addCell(getBillHeaderCell("ID"));
            billTable.addCell(getBillHeaderCell("Room No."));
            billTable.addCell(getBillHeaderCell("Description"));
            billTable.addCell(getBillHeaderCell("Arrival"));
            billTable.addCell(getBillHeaderCell("Departure"));
            billTable.addCell(getBillHeaderCell("Price rate"));
            billTable.addCell(getBillHeaderCell("Days"));

            billTable.addCell(getBillRowCell(String.format("%s", billRowIndex)));
            billTable.addCell(getBillRowCell(String.format(reservation.getRoomID())));
            getRoomInfo();
            billTable.addCell(getBillRowCell(String.format("Quality Level: %s \n Bed Number: %s \n Smoking: %s \n Adjoining: %s",
                    roomInfo.get(0).getQuality(), roomInfo.get(0).getBedNumber(), roomInfo.get(0).getSmoking(), roomInfo.get(0).getAdjoining())));
            billTable.addCell(getBillRowCell(String.format(reservation.getCheckInDate().toString())));
            billTable.addCell(getBillRowCell(String.format(reservation.getCheckOutDate().toString())));
            billTable.addCell(getBillRowCell(String.format("%.2f", (float) roomInfo.get(0).getMaxRate())));
            billTable.addCell(getBillRowCell(String.format("%s", reservation.getTotalDays())));

            for (int i = 0; i <= 14; i++) {
                billTable.addCell(getBillRowCell(""));
                billTable.addCell(getBillRowCell(""));
                billTable.addCell(getBillRowCell(""));
                billTable.addCell(getBillRowCell(""));
                billTable.addCell(getBillRowCell(""));
                billTable.addCell(getBillRowCell(""));
            }

            PdfPTable feesTable = new PdfPTable(3); //one page contains 15 records
            feesTable.setWidthPercentage(100);
            feesTable.setWidths(new float[]{1, 5, 2});
            feesTable.addCell(getBillHeaderCell("ID"));
            feesTable.addCell(getBillHeaderCell("Description"));
            feesTable.addCell(getBillHeaderCell("Fee"));
            getFees();
            for (int i = 0; i < fees.size(); i++) {
                feesTable.addCell(getBillRowCell(String.format("%s", i + 1)));
                feesTable.addCell(getBillRowCell(String.format(fees.get(i).getDescription())));
                feesTable.addCell(getBillRowCell(String.format("%s ", fees.get(i).getFee())));
                totalFeesCounter = totalFeesCounter + fees.get(i).getFee();
            }

            PdfPTable validity = new PdfPTable(1);
            validity.setWidthPercentage(100);
            validity.addCell(getValidityCell(" "));
            validity.addCell(getValidityCell(" * Thank you for staying with us at Linnaeus Hotel! * "));
            validity.addCell(getValidityCell(" * We are looking forward to seeing you again * "));
            validity.addCell(getValidityCell(" "));
            PdfPCell summaryL = new PdfPCell(validity);
            summaryL.setColspan(3);
            summaryL.setPadding(1.0f);
            feesTable.addCell(summaryL);

            PdfPTable accounts = new PdfPTable(2);
            accounts.setWidthPercentage(100);
            accounts.addCell(getAccountsCell("Subtotal"));
            accounts.addCell(getAccountsCellR(String.format("%.2f", (float) reservation.getToPay())));
            accounts.addCell(getAccountsCell("Fees"));
            accounts.addCell(getAccountsCellR(String.format("%s", totalFeesCounter)));
            accounts.addCell(getAccountsCell("Tax(2.5%)"));
            accounts.addCell(getAccountsCellR(String.format("%.2f", (((float) totalFeesCounter + (float) reservation.getToPay()) * 2.5) / 100)));
            accounts.addCell(getAccountsCell("Total"));
            accounts.addCell(getAccountsCellR(String.format("%.2f", (float) totalFeesCounter + (float) reservation.getToPay() + (((float) totalFeesCounter + (float) reservation.getToPay()) * 2.5) / 100)));

            PdfPCell summaryR = new PdfPCell(accounts);
            summaryR.setColspan(4);
            feesTable.addCell(summaryR);

            FontSelector selector = new FontSelector();
            Font smallFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 7);
            selector.addFont(smallFont);
            Phrase signature = fs.process("\nGuest Signature: ___________________________________________________\n");
            Phrase info = selector.process("I have received the goods and / or services in the amount shown heron. " +
                    "I agree that my liability for this bill is not waived and agree to be held personally liable in the " +
                    "event that indicated person, company, or associate fails to pay for any part or the full amount of " +
                    "these charges. If a credit card charge, I further agree to perform the obligations set forth in the " +
                    "cardholder's agreement with the issuer."); // customer information

            document.open();
            document.add(image);
            document.add(irhTable);
            document.add(bill);
            document.add(name);
            document.add(contact);
            document.add(address);
            document.add(billTable);
            document.add(feesTable);
            document.add(signature);
            document.add(info);
            document.close();
            file.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setHeader() {
    }

    public static PdfPCell getIRHCell(String text, int alignment) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        /*	font.setColor(BaseColor.GRAY);*/
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public static PdfPCell getIRDCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    public static PdfPCell getBillHeaderCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        return cell;
    }

    public static PdfPCell getBillRowCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }

    public static PdfPCell getBillFooterCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }

    public static PdfPCell getValidityCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorder(0);
        return cell;
    }

    public static PdfPCell getAccountsCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setPadding(5.0f);
        return cell;
    }

    public static PdfPCell getAccountsCellR(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthTop(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(5.0f);
        cell.setPaddingRight(20.0f);
        return cell;
    }

    public static PdfPCell getdescCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        return cell;
    }

    private void getRoomInfo() {
        roomInfo.clear();
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(reservation.getRoomID());
        parameters.add(reservation.getLocation());
        try {
            String query = "SELECT * FROM ROOMS WHERE ID=? AND LOCATION=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                roomInfo.add(new Room(rs.getString("ID"), rs.getString("QUALITY"),
                        rs.getString("BEDNUMBER"), rs.getString("SMOKING"),
                        rs.getString("ADJOINING"), rs.getInt("MAXRATE")));
            }
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }

    private void getFees() {
        fees.clear();
        DbConnect connection = new DbConnect();
        ArrayList parameters = new ArrayList();
        parameters.add(reservation.getId());
        try {
            String query = "SELECT * FROM FEES WHERE RESERVATIONID=?";
            ResultSet rs = connection.executeWithParameters(query, parameters);
            while (rs.next()) {
                fees.add(new Fee(rs.getInt("ID"), rs.getInt("RESERVATIONID"),
                        rs.getInt("FEE"), rs.getString("DESCRIPTION")));
            }
        } catch (Exception ex) {
            System.err.println(ex);
            alert.showErrorMessage(ex);
        } finally {
            connection.closeConnection();
        }
    }
}