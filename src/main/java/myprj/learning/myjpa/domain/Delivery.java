package myprj.learning.myjpa.domain;


import jakarta.persistence.*;

@Entity(name = "DELIVERY")
@SequenceGenerator(name = "DELIVERY_SEQ_GEN", sequenceName = "DELIVERY_SEQUENCE")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_SEQ_GEN")
    private Long seq;
    private DeliveryStatus deliveryStatus;
    @Embedded
    private Address address;

    public Delivery() {
    }

    public Delivery(Address address) {
        this.setDeliveryStatus(DeliveryStatus.PREPARE);
        this.setAddress(address);
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
