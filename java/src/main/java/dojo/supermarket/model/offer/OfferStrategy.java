package dojo.supermarket.model.offer;

import dojo.supermarket.model.*;

import java.util.Date;

public class OfferStrategy {
    Date startDate;
    Date endDate;

    public OfferStrategy(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isValid() {
        Date currentDate = new Date();

        if(startDate == null && endDate == null) {
            return true;
        }

        return !(currentDate.before(startDate) || currentDate.after(endDate));
    }

    public void apply(Product product,
               double quantity,
               double unitPrice,
               double argument,
               Receipt receipt) {

    }
}
