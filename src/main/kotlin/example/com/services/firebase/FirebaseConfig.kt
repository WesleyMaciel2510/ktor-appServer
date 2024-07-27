package example.com.services.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream
import org.slf4j.LoggerFactory
import kotlinx.serialization.Serializable
import java.io.IOException

object FirebaseConfig {
    private val logger = LoggerFactory.getLogger(FirebaseConfig::class.java)

    fun initialize() {
        try {
            val serviceAccount = FileInputStream(ConfigLoader.config.serviceAccount)
            //logger.info("Loading service account from: ${ConfigLoader.config.serviceAccount}")
            //logger.info("Service account loaded successfully.")

            //logger.info("Setting Firebase options with database URL: ${ConfigLoader.config.databaseUrl}")
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(ConfigLoader.config.databaseUrl)
                .build()
            //logger.info("Initializing FirebaseApp...")

            FirebaseApp.initializeApp(options)
            //logger.info("FirebaseApp initialized successfully.")
        } catch (e: IOException) {
            //.error("IOException occurred during Firebase initialization", e)
            e.printStackTrace()
            throw RuntimeException("Failed to initialize Firebase", e)
        } catch (e: Exception) {
            //logger.error("Unexpected exception occurred during Firebase initialization", e)
            e.printStackTrace()
            throw RuntimeException("Failed to initialize Firebase", e)
        }

        //logger.info("FirebaseConfig initialization completed.")
    }
}