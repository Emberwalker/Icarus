package io.drakon.icarus.lib

import java.io.File

import io.drakon.icarus.Icarus
import net.minecraftforge.common.config.Configuration

/**
 * Configuration handler.
 *
 * @author Arkan <arkan@drakon.io>
 */
object Config {

  var port = 9000
  var contextPath = "/*"

  def loadConfig(f:File) {
    Icarus.log.info("Loading configuration [{}]...", f)
    val config = new Configuration(f)
    config.load()

    port = config.getInt("port", "jetty", port, 1024, 65535, "Jetty port")
    contextPath = config.getString("contextPath", "jetty", contextPath, "Prometheus context path - you probably shouldn't touch this")

    config.save()
    Icarus.log.info("Configuration loaded.")
  }

}
