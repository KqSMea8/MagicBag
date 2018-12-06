package org.github.helixcs.googleapis;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseStart {
    public static void main(String[] args) {
        FirebaseOptions firebaseOptions  = new FirebaseOptions.Builder().build();

        FirebaseApp.initializeApp(firebaseOptions);
    }
}
