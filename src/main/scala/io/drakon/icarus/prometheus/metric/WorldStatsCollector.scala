package io.drakon.icarus.prometheus.metric

import java.util
import io.prometheus.client.Collector.MetricFamilySamples.Sample
import net.minecraft.server.MinecraftServer
import net.minecraft.tileentity.TileEntity

import scala.collection.JavaConversions._

import io.prometheus.client.Collector
import io.prometheus.client.Collector.{MetricFamilySamples, Type}

/**
 * Collector for MC world statistics (entity/TE/active chunk counts)
 *
 * @author Arkan <arkan@drakon.io>
 */
class WorldStatsCollector extends Collector {

  override def collect(): util.List[MetricFamilySamples] = {
    List.concat(
      getEntitySamples,
      getTESamples,
      getChunkSamples
    )
  }

  private def getEntitySamples: List[MetricFamilySamples] = {
    val entCount = MinecraftServer.getServer.worldServers.map(w => w.loadedEntityList.size()).sum
    List(new MetricFamilySamples(
      "mc_loaded_entities_total",
      Type.GAUGE,
      "Total loaded entities across all loaded world servers.",
      List(new Sample(
        "mc_loaded_entities_total",
        List[String](),
        List[String](),
        entCount
      ))
    ))
  }

  private def getTESamples: List[MetricFamilySamples] = {
    val worldServers = MinecraftServer.getServer.worldServers
    val totalTEs = worldServers.map(w => w.loadedTileEntityList.size()).sum
    val tickingTEs = worldServers.map(w => w.loadedTileEntityList.asInstanceOf[util.List[TileEntity]]
      .count(e => e.canUpdate)).sum
    val staticTEs = totalTEs - tickingTEs

    val samples = List(
      new Sample(
        "mc_loaded_tile_entities_total",
        List[String]("ticking"),
        List[String]("true"),
        tickingTEs
      ),
      new Sample(
        "mc_loaded_tile_entities_total",
        List[String]("ticking"),
        List[String]("false"),
        staticTEs
      )
    )

    List(new MetricFamilySamples(
      "mc_loaded_tile_entities_total",
      Type.GAUGE,
      "Total loaded tile entities across all loaded world servers.",
      samples
    ))
  }

  private def getChunkSamples: List[MetricFamilySamples] = {
    val chunks = MinecraftServer.getServer.worldServers.map(w => w.getChunkProvider.getLoadedChunkCount).sum

    List(new MetricFamilySamples(
      "mc_loaded_chunks_total",
      Type.GAUGE,
      "Total loaded chunks across all loaded world servers.",
      List(new Sample(
        "mc_loaded_chunks_total",
        List[String](),
        List[String](),
        chunks
      ))
    ))
  }

}
