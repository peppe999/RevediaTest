package it.ingsw.revedia.utilities;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import it.ingsw.revedia.model.User;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleManager {
    public static User validateToken(String idTokenString) {
        User user = null;

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList("515813722957-cf1qne959r79ib23jd9i9jd5i5j1s67a.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken = null;

        try {
            idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                String userId = payload.getSubject();

                String email = payload.getEmail();
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                user = new User();
                user.setFirstName(givenName);
                user.setLastName(familyName);
                user.setMail(email);

                return user;
            } else {
                System.out.println("Invalid ID token.");
                return null;
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
