package myprj.learning.myjpa.domain;


import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {
    private String city;
    private String street;
    private String detail;


    public Address() {
        // 이게 기본 생성자가 있으면 안 될 거 같은데 기본 생성자를 안 만들어 두면 아래 같은 에러가 나는데...?
        // org.hibernate.InstantiationException: Unable to locate constructor for embeddable
        // Address 는 값 하나로써 사용해야 하기 때문에 생성할 때 딱 한 번만 값을 넣어줄 수 있고 그 외에는 수정 못하도록 막아야 함
        // 그래서 기본 생성자는 만들면 안 되는데..
    }

    public Address(String city, String street, String detail) {
        this.city = city;
        this.street = street;
        this.detail = detail;
    }

    // 값타입은 수정할 수 없도록 만들어야 해서 setter 를 만들지 않거나 private 으로 만듦.
    // 값타입의 내용물은 생성자에만 최초로 넣어줄 수 있고 그 후로는 수정할 수 없음
    // 왜냐하면 값타입은 그 자체로 값 하나로써 쓰여야 함.
    // 그런데 값타입은 어쨌든 자바 문법상 클래스라서 값을 대입하면 복사되는 게 아니라 참조값이 넘어감.
    // 이건 자바 언어 작동 방식이기 때문에 직접적으로 막을 방법이 없음.
    // 따라서 간접적으로 수정을 못하는 방식을 취한 것.
    // int a = 3;
    // int b = a;
    // a = 7;
    // a 에 있던 '3' 이라는 값을 b 에 넣어두고 그 뒤에 a 를 7로 바꾸면 a = 7 / b = 3 이 됨.
    // Address a = new Address("seoul", "jongro", "hello");
    // Address b = a;
    // a.setStreet("gangnam");
    // a 에 있던 seoul 값만 gangnam 으로 바꾸고 싶었는데 b 에 있던 seoul 값까지 따라서 바뀜.
    // Address b = a; 할 때 값 자체가 넘어간 게 아니라 참조값이 넘어갔기 때문.
    // 이런 의도하지 않은 상황을 예방하기 위해서 값타입은 수정을 못하게 막아 놓음.
    
    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getDetail() {
        return detail;
    }

    // @Embeddable 값타입 만들 때는 equals 나 hashCode 를 꼭 재정의 해줘야 함
    // 두 객체가 같은지 다른지 확인할 때 내용물 가지고 확인
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(detail, address.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, detail);
    }
}
