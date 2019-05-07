import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.parachutesmethod.models.Customer;
import org.parachutesmethod.models.OrderItem;
import org.parachutesmethod.models.OrderPOJO;

import java.io.IOException;
import java.util.Date;

public class RequestGenerator {

    @Test
    public void createExamplePdfGenerationRequest() {
        int numberOfOrderItems = 10;

        OrderPOJO order = new OrderPOJO();

        Customer customer = new Customer();
        customer.setCustomerId((int) (Math.random() * ((9999999 - 0) + 1)));
        customer.setName("Max Mustermann");
        customer.setStreet("Musterstrasse 123");
        customer.setZipCode("12345");
        customer.setCity("Musterstadt");
        customer.setCountry("Germany");

        order.setCustomer(customer);

        order.setOrderDate(new Date());

        for (int i = 0; i < numberOfOrderItems; i++) {
            OrderItem item = new OrderItem();

            item.setProductId((int) (Math.random() * ((9999999 - 0) + 1)));
            item.setAmount(i + (i % 3) + 1);
            item.setItemDescription("DESCRIPTION");
            item.setItemPrice(i * 12.47 / 13 + 20);

            order.getOrderItems().add(item);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(System.out, order);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
