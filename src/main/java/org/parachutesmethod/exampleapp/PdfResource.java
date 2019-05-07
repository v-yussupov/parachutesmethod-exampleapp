package org.parachutesmethod.exampleapp;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.annotations.Api;
import org.parachutesmethod.annotations.ParachuteMethod;
import org.parachutesmethod.models.Customer;
import org.parachutesmethod.models.OrderItem;
import org.parachutesmethod.models.OrderPOJO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/invoices")
@Api(value = "pdfresource", description = "Generate an invoice PDF for a given order.")
public class PdfResource {

    private static Logger LOGGER = LoggerFactory.getLogger(PdfResource.class);

    @POST
    @Path("generatePDF")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @ParachuteMethod(backupRoute = true)
    public byte[] generateInvoice(OrderPOJO input) {
        byte[] result = new byte[0];

        if (input != null && input.getCustomer() != null && input.getOrderItems() != null) {
            try {
                LOGGER.info("Start PDF invoice generation");

                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                Document document = new Document();
                PdfWriter.getInstance(document, bout);

                document.open();

                SimpleDateFormat sf = new SimpleDateFormat("MMM dd, yyyy");

                LOGGER.info("Add company header to invoice");
                Paragraph p;
                p = new Paragraph("Example Company");
                p.setAlignment(Element.ALIGN_RIGHT);
                document.add(p);
                p = new Paragraph(sf.format(input.getOrderDate()));
                p.setAlignment(Element.ALIGN_RIGHT);
                document.add(p);

                LOGGER.info("Add customer address to invoice");
                addCustomerAddress(document, input.getCustomer());

                document.add(Chunk.NEWLINE);

                LOGGER.info("Add number to invoice");
                Paragraph invNo = new Paragraph("Invoice No. " + String.format("%d", (int) new Date().getTime()));
                invNo.setAlignment(Element.ALIGN_LEFT);
                document.add(invNo);

                LOGGER.info("Add table of order items to invoice");
                double orderTotal = addOrderItemTable(document, input.getOrderItems());

                LOGGER.info("Add total table to invoice");
                addTotalsTable(document, orderTotal);

                document.close();

                result = bout.toByteArray();
                bout.close();

                LOGGER.info("A PDF for the invoice is successfully generated");
            } catch (IOException | DocumentException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        } else {
            LOGGER.warn("Input is not valid.");
        }

        return result;
    }

    private void addCustomerAddress(Document document, Customer customer) throws DocumentException {
        PdfPTable customerAddressTable = new PdfPTable(1);

        customerAddressTable.setWidthPercentage(100);
        customerAddressTable.setSpacingBefore(10);
        customerAddressTable.setSpacingAfter(10);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.addElement(new Paragraph(customer.getCustomerId()));
        cell.addElement(new Paragraph(customer.getName()));
        cell.addElement(new Paragraph(customer.getStreet()));
        cell.addElement(new Paragraph(String.format("%s %s", customer.getZipCode(), customer.getCity())));
        cell.addElement(new Paragraph(customer.getCountry()));

        customerAddressTable.addCell(cell);

        document.add(customerAddressTable);
    }

    private double addOrderItemTable(Document document, List<OrderItem> orderItems) throws DocumentException {
        double sumTotal = 0.0;

        String[] columns = {"Product Id", "Item", "Price", "Amount", "Total"};

        PdfPTable orderItemTable = new PdfPTable(columns.length);
        orderItemTable.setWidthPercentage(100);
        orderItemTable.setSpacingBefore(10);
        orderItemTable.setSpacingAfter(10);

        LOGGER.info("Add header to order item table");
        for (String columnTitle : columns) {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(columnTitle));
            orderItemTable.addCell(header);
        }

        for (OrderItem item : orderItems) {
            LOGGER.info("Add new row to order item table for item with id='" + item.getProductId() + "'.");

            PdfPCell productIdCell = new PdfPCell(new Phrase(String.format("%d", item.getProductId())));
            productIdCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            orderItemTable.addCell(productIdCell);

            PdfPCell itemCell = new PdfPCell(new Phrase(item.getItemDescription()));
            itemCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            orderItemTable.addCell(itemCell);

            PdfPCell priceCell = new PdfPCell(new Phrase(String.format("%.2f EUR", item.getItemPrice())));
            priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            orderItemTable.addCell(priceCell);

            PdfPCell amountCell = new PdfPCell(new Phrase(String.format("%d", item.getAmount())));
            amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            orderItemTable.addCell(amountCell);

            double total = item.getItemPrice() * item.getAmount();
            sumTotal += total;

            PdfPCell totalCell = new PdfPCell(new Phrase(String.format("%.2f EUR", total)));
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            orderItemTable.addCell(totalCell);
        }

        document.add(orderItemTable);

        return sumTotal;
    }

    private void addTotalsTable(Document document, double sumTotals) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);

        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(1);
        header.setPhrase(new Phrase("Total:"));
        table.addCell(header);

        PdfPCell totalsCell = new PdfPCell(new Phrase(String.format("%.2f EUR", sumTotals)));
        totalsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(totalsCell);

        document.add(table);
    }
}
