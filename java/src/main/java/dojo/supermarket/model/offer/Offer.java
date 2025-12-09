package dojo.supermarket.model.offer;

import dojo.supermarket.model.receipt.Receipt;

import java.util.Date;

public abstract class Offer {
    double argument;
    Date startDate;
    Date endDate;

    public Offer(Date startDate, Date endDate, double argument) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.argument = argument;
    }

    public Offer(double argument) {
        this.startDate = null;
        this.endDate = null;
        this.argument = argument;
    }

    public boolean isValid() {
        Date currentDate = new Date();

        if(startDate == null && endDate == null) {
            return true;
        }

        return !(currentDate.before(startDate) || currentDate.after(endDate));
    }

    public void apply(Receipt receipt){};
}
