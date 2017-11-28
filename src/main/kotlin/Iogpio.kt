import java.io.File

object Iogpio : AutoCloseable {
  private infix fun String.into(file: String) {
    File(file).printWriter().use { it.println(this) }
  }

  private val pins: MutableMap<Int, Pin> = mutableMapOf()

  operator fun get(id: Int): Pin {
    pins[id]?.let { return it }
    synchronized(pins) {
      pins[id]?.let { return it }
      val pin = Pin(id)
      pins[id] = pin
      return pin
    }
  }

  override fun close() {
    pins.values.forEach { it.close() }
  }

  class Pin(private val id: Int) : AutoCloseable {
    init {
      "$id" into "/sys/class/gpio/export"
      "out" into "/sys/class/gpio/gpio$id/direction"
    }

    override fun close() {
      "$id" into "/sys/class/gpio/unexport"
    }

    fun low() {
      "1" into "/sys/class/gpio/gpio$id/value"
    }

    fun high() {
      "0" into "/sys/class/gpio/gpio$id/value"
    }
  }

  init {
    //Runtime.getRuntime().addShutdownHook(Thread { close() })
  }
}
