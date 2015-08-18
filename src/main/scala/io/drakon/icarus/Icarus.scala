package io.drakon.icarus

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLPostInitializationEvent, FMLInitializationEvent, FMLPreInitializationEvent}
import io.drakon.icarus.jetty.JettyHandler
import io.drakon.icarus.lib.Config
import io.drakon.icarus.prometheus.PrometheusHandler
import org.apache.logging.log4j.LogManager

/**
 * Icarus - Prometheus stat tracker for Minecraft Forge servers.
 *
 * @author Arkan <arkan@drakon.io>
 */
@Mod(modid = "icarus", name = "Icarus", version = "@VERSION@", acceptableRemoteVersions = "*", modLanguage = "scala")
object Icarus {

  val log = LogManager.getLogger("Icarus")

  @EventHandler
  def preinit(evt:FMLPreInitializationEvent) {
    log.info("Preinit.")
    Config.loadConfig(evt.getSuggestedConfigurationFile)
    PrometheusHandler.init()
    JettyHandler.init()
  }

  @EventHandler
  def init(evt:FMLInitializationEvent) {
    log.info("Init.")
  }

  @EventHandler
  def postinit(evt:FMLPostInitializationEvent) {
    log.info("Postinit.")
  }

}
