package io.drakon.icarus.prometheus.metric

import java.util
import scala.collection.JavaConversions._

import io.prometheus.client.Collector
import io.prometheus.client.Collector.MetricFamilySamples.Sample
import io.prometheus.client.Collector.{MetricFamilySamples, Type}

import net.minecraft.server.MinecraftServer
import net.minecraft.world.World

/**
 * Prometheus collector for TPS.
 *
 * Based on code from CoFHCore (github.com/CoFH/CoFHCore cofh.core.command.CommandTPS)
 *
 * @author Arkan <arkan@drakon.io>
 */
class TickCollector extends Collector {

  override def collect(): util.List[MetricFamilySamples] = {
    if (MinecraftServer.getServer == null) return List()
    val tpsSample = new MetricFamilySamples(
      "mc_tps",
      Type.GAUGE,
      "Ticks per second on the server.",
      List(new Sample(
        "mc_tps",
        List[String](),
        List[String](),
        getTps(null)
      ))
    )

    val tickMsSample = new MetricFamilySamples(
      "mc_average_tick_milliseconds",
      Type.GAUGE,
      "Average time for a single tick on the server.",
      List(new Sample(
        "mc_average_tick_milliseconds",
        List[String](),
        List[String](),
        getTickMs(null)
      ))
    )

    List(tpsSample, tickMsSample)
  }

  private def getTickSum(times: Array[Long]): Double = {
    if (times == null) return 0.0D

    times.sum / times.length
  }

  private def getTickMs(w: World): Double = {
    var ms: Array[Long] = null
    if (w == null) {
      ms = MinecraftServer.getServer.tickTimeArray
    } else {
      ms = MinecraftServer.getServer.worldTickTimes.get(w.provider.dimensionId)
    }

    getTickSum(ms) * 1.0E-006D
  }

  private def getTps(w: World): Double = {
    val tps = 1000.0D / getTickMs(w)
    if (tps > 20.0D) 20.0D else tps
  }

}
