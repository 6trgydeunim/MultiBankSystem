package multibank.model;

public class Customer {

    private String name;
    private String aadhaar;
    private String dob;
    private boolean kycDone;
    private Account account;

    public Customer(String name, String aadhaar, String dob, Account account) {
        this.name = name;
        this.aadhaar = aadhaar;
        this.dob = dob;
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


    public void completeKYC() {
        kycDone = true;
    }

    public boolean isKycDone() {
        return kycDone;
    }
}
