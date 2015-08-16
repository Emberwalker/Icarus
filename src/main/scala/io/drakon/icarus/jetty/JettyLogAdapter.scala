package io.drakon.icarus.jetty

import org.apache.logging.log4j
import org.apache.logging.log4j.LogManager
import org.eclipse.jetty.util.log.Logger

/**
 * Log4j2 adapter for Jetty.
 *
 * @author Arkan <arkan@drakon.io>
 */
class JettyLogAdapter(_log:log4j.Logger) extends Logger {

  def this() {
    this(LogManager.getLogger)
  }

  def this(name: String) {
    this(LogManager.getLogger(name))
  }

  override def debug(msg: String, thrown: Throwable) {
    _log.debug(msg, thrown)
  }

  override def debug(msg: String, args: AnyRef*) {
    _log.debug(msg, args)
  }

  override def debug(msg: String, value: Long) {
    _log.debug(msg, value.toString)
  }

  override def debug(thrown: Throwable) {
    _log.debug(thrown)
  }

  override def info(msg: String, thrown: Throwable) {
    _log.info(msg, thrown)
  }

  override def info(msg: String, args: AnyRef*) {
    _log.info(msg, args)
  }

  override def info(thrown: Throwable) {
    _log.info(thrown)
  }

  override def warn(msg: String, thrown: Throwable) {
    _log.warn(msg, thrown)
  }

  override def warn(msg: String, args: AnyRef*) {
    _log.warn(msg, args)
  }

  override def warn(thrown: Throwable) {
    _log.warn(thrown)
  }

  override def ignore(ignored: Throwable) {
    // I dun' bloody know. Just ignore it?
  }

  override def getName: String = _log.getName

  override def getLogger(name: String): Logger = new JettyLogAdapter(name)

  override def isDebugEnabled: Boolean = _log.isDebugEnabled

  override def setDebugEnabled(enabled: Boolean) {
    _log.error("Something tried to toggle debug on this logger. Log4j2 is shit and doesn't support that. Ignoring.",
               new Throwable("Invalid logger call setDebugEnabled()"))
  }

}
