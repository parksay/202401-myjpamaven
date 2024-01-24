package myprj.learning.myjpa.domain;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER")
@SequenceGenerator(name = "MEMBER_SEQ_GEN", sequenceName = "MEMBER_SEQUENCE")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GEN")
    private Long seq;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_ID")
    private String userid;

    @Column(name = "USER_PSW")
    private String psw;

    @OneToMany(mappedBy = "member")
    private List<Orders> ordersList = new ArrayList<Orders>();

    @Embedded
    private Address address;

    public Member() {
    }

    public Member(String name, String userid, String psw, Address address) {
        this.setName(name);
        this.setUserid(userid);
        this.setPsw(psw);
        this.setAddress(address);
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public String toString() {
        return "Member{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                ", userid='" + userid + '\'' +
                ", psw='" + psw + '\'' +
                ", ordersList=" + ordersList +
                ", address.getCity()=" + address.getCity() +
                ", address.getStreet()=" + address.getStreet() +
                ", address.getDetail()=" + address.getDetail() +
                '}';
    }
}
