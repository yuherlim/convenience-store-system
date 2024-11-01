
/**
 *
 * @author ong58
 */
public abstract class Person {

    //Data field
    protected String name;
    protected String ic;
    protected String birthdate;
    protected String phoneNum;
    protected String address;

    //Constructor
    protected Person() {
        name = "";
        ic = "";
        birthdate = "";
        phoneNum = "";
        address = "";
    }

    protected Person(String name, String ic, String birthdate, String phoneNum, String address) {
        this.name = name;
        this.ic = ic;
        this.birthdate = birthdate;
        this.phoneNum = phoneNum;
        this.address = address;
    }

    //Setter & getter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "name=" + name + ", ic=" + ic + ", birthdate=" + birthdate + ", phoneNum=" + phoneNum + ", address=" + address;
    }
}
