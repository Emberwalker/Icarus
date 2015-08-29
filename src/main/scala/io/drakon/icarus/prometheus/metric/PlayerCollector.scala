package io.drakon.icarus.prometheus.metric

import java.util
import scala.collection.JavaConversions._

import io.prometheus.client.Collector
import io.prometheus.client.Collector.MetricFamilySamples.Sample
import io.prometheus.client.Collector.{MetricFamilySamples, Type}

import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer

/**
 * Collector for player statistics (logged in count/latency)
 */
class PlayerCollector extends Collector {

  override def collect(): util.List[MetricFamilySamples] = {
    if (MinecraftServer.getServer == null) return List()
    List.concat(
      getPlayerCountSamples,
      getPlayerLatencyAvgSamples,
      getPlayerLatencySamples
    )
  }

  private def getPlayerCountSamples: List[MetricFamilySamples] = {
    val plCount = MinecraftServer.getServer.getCurrentPlayerCount

    List(new MetricFamilySamples(
      "mc_lodded_in_players_total",
      Type.GAUGE,
      "Total number of logged in players.",
      List(new Sample(
        "mc_lodded_in_players_total",
        List[String](),
        List[String](),
        plCount
      ))
    ))
  }

  private def getPlayerLatencyAvgSamples: List[MetricFamilySamples] = {
    val players = MinecraftServer.getServer.getConfigurationManager.playerEntityList.asInstanceOf[util.List[EntityPlayerMP]]
    val latencies = players.map(p => p.ping)
    val avg = if (latencies.isEmpty) 0 else latencies.sum / latencies.length

    List(new MetricFamilySamples(
      "mc_average_player_latency_milliseconds",
      Type.GAUGE,
      "Average latency for all players on the server.",
      List(new Sample(
        "mc_average_player_latency_milliseconds",
        List[String](),
        List[String](),
        avg
      ))
    ))
  }

  private def getPlayerLatencySamples: List[MetricFamilySamples] = {
    val players = MinecraftServer.getServer.getConfigurationManager.playerEntityList.asInstanceOf[util.List[EntityPlayerMP]]
    val samples = players.map(p =>new Sample(
      "mc_player_latency_milliseconds",
      List("player"),
      List(p.getCommandSenderName),
      p.ping
    ))

    List(new MetricFamilySamples(
      "mc_player_latency_milliseconds",
      Type.GAUGE,
      "Latency per player on the server.",
      samples
    ))
  }

}
