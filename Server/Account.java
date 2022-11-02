public class Account {
    private static String accountName;
    private static String accountPassword;

    public Account(String username, String password){
        accountName = username;
        accountPassword = password;
    }

    public String getUsername(){
        return accountName;
    }

    public String getPassword(){
        return accountPassword;
    }
}
