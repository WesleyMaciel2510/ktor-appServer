package example.com.services.firebase

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.io.File

@Serializable
data class Config(
    val serviceAccount: String,
    val databaseUrl: String
)

object ConfigLoader {
    private val logger = LoggerFactory.getLogger(ConfigLoader::class.java)
    private var ConfigFilePath = "keys/firebase-keys.json"

    val config: Config by lazy {
        val json = File(ConfigFilePath).readText()
        logger.info("Configuration JSON: $json")
        Json.decodeFromString<Config>(json).also {
            logger.info("Configuration loaded: serviceAccount = ${it.serviceAccount}, databaseUrl = ${it.databaseUrl}")
        }
    }
}
