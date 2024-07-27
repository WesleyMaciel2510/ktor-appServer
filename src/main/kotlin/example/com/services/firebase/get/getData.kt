package example.com.services.firebase.get

import com.google.firebase.database.*
import example.com.services.firebase.ConfigLoader
import kotlinx.coroutines.suspendCancellableCoroutine
import org.slf4j.LoggerFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object FirebaseGet {
    private val logger = LoggerFactory.getLogger(FirebaseGet::class.java)

    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance(ConfigLoader.config.databaseUrl)
    }

    private val dataRef: DatabaseReference by lazy {
        database.getReference("ktor/getString")
    }

    suspend fun getData(): String? {
        logger.info("getData called...")
        return try {
            logger.info("Attempting to get data from Firebase...")
            val snapshot = dataRef.getValueOnce()
            logger.info("DataSnapshot received: $snapshot")
            val value = snapshot.getValue(String::class.java)
            logger.info("Data converted to String: $value")
            value
        } catch (e: Exception) {
            logger.error("Failed to get data", e)
            null
        }
    }

    private suspend fun DatabaseReference.getValueOnce(): DataSnapshot =
        suspendCancellableCoroutine { continuation ->
            addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    logger.info("onDataChange called with snapshot: $snapshot")
                    continuation.resume(snapshot)
                }

                override fun onCancelled(error: DatabaseError) {
                    logger.error("onCancelled called with error: $error")
                    continuation.resumeWithException(error.toException())
                }
            })
        }
}
