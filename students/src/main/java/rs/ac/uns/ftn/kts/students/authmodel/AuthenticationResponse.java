package rs.ac.uns.ftn.kts.students.authmodel;

public class AuthenticationResponse {

    private final String jwt;
    private final String uloga;

    public AuthenticationResponse(String jwt, String uloga) {
        this.jwt = jwt;
        this.uloga = uloga;
    }

    public String getJwt() {
        return jwt;
    }

    public String getUloga() {
        return uloga;
    }
}
