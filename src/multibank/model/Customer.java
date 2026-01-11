package multibank.model;

public class Customer {

    private String name;
    private String aadhaar;
    private String dob;
    private String password;
    private boolean kycDone;
    private Account account;

    public Customer(String name, String aadhaar, String dob,
                    String password, Account account) {
        this.name = name;
        this.aadhaar = aadhaar;
        this.dob = dob;
        this.password = password;
        this.account = account;
        this.kycDone = false;
    }

    public String getName() {
        return name;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public Account getAccount() {
        return account;
    }

    public boolean verifyPassword(String pwd) {
        return password.equals(pwd);
    }

    public void completeKYC() {
        kycDone = true;
    }

    public boolean isKycDone() {
        return kycDone;
    }
}
