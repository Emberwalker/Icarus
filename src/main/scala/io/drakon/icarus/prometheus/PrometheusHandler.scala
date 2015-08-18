package io.drakon.icarus.prometheus

import io.drakon.icarus.Icarus
import io.drakon.icarus.prometheus.metric.{PlayerCollector, WorldStatsCollector, TickCollector}

import io.prometheus.client.hotspot

/**
 * Prometheus registry handler.
 *
 * @author Arkan <arkan@drakon.io>
 */
object PrometheusHandler {

  private var __initialized = false

  /**
   * Attach Prometheus' and Icarus' default collectors.
   */
  def init() {
    if (!__initialized) {
      Icarus.log.info("Attaching HotSpot VM metric collectors...")
      hotspot.DefaultExports.initialize()

      Icarus.log.info("Attaching Icarus metric collectors...")
      new TickCollector().register()
      new WorldStatsCollector().register()
      new PlayerCollector().register()

      Icarus.log.info("Collectors attached.")
      __initialized = true
    }
  }

}
