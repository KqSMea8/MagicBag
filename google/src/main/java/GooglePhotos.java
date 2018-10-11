import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;
import com.google.photos.library.v1.proto.Album;

import java.io.FileInputStream;

/**
 * @Author: Helixcs
 * @Time:9/30/18
 */
public class GooglePhotos {
    private static Credentials getCredentials()throws  Exception{
        return GoogleCredentials.fromStream(new FileInputStream("/Users/helix/IdeaProjects/MagicBag/google/src/main/resources/Helixcs-ad127cbe0f78.json"));

    }
    public static void main(String[] args) throws Exception{

        getCredentials();
        // Set up the Photos Library Client that interacts with the API
        PhotosLibrarySettings settings = PhotosLibrarySettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(getCredentials())).build();

        try (PhotosLibraryClient photosLibraryClient = PhotosLibraryClient.initialize(settings)) {

            // Create a new Album  with at title
            Album createdAlbum = photosLibraryClient.createAlbum("My Album");

            // Get some properties from the album, such as its ID and product URL
            String id = createdAlbum.getId();
            String url = createdAlbum.getProductUrl();

        } catch (ApiException e) {
            // Error during album creation
        }

    }
}
