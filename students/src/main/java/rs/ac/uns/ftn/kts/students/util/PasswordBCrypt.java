package rs.ac.uns.ftn.kts.students.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordBCrypt {
    private static int workload = 10;

    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
    }

}
