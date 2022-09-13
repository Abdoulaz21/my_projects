package computerShop;

import java.util.Objects;

public class SerialNumber {
    private final String serialNumber;

    public SerialNumber(String serialNumber) throws IllegalArgumentException {
        if (!serialNumber.matches("[a-zA-Z0-9]*")){
            throw new IllegalArgumentException("Bad Serial Number!");
        }
        this.serialNumber = serialNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SerialNumber sn){
            return this.getSerialNumber().equals(sn.getSerialNumber());
        }
        return false;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
