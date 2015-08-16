package io.drakon.icarus.jetty

import io.drakon.icarus.Icarus
import io.drakon.icarus.lib.Config
import io.prometheus.client.exporter.MetricsServlet

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHandler

/**
 * Wrapper around Jetty for loading Prometheus.
 *
 * @author Arkan <arkan@drakon.io>
 */
object JettyHandler {

  var server:Server = null

  def init() {
    Icarus.log.info("Setting up Jetty instance...")
    server = new Server(Config.port)

    val handler = new ServletHandler
    server.setHandler(handler)

    handler.addServletWithMapping(classOf[MetricsServlet], Config.contextPath)

    startJetty()
  }

  def startJetty() {
    Icarus.log.info(s"Starting Jetty on port [${Config.port}] with context path [${Config.contextPath}]...")
    server.start()
    Icarus.log.info("Jetty started.")
  }

  def stopJetty() {
    Icarus.log.info("Stopping Jetty...")
    server.stop()
    Icarus.log.info("Jetty stopped.")
  }

}
