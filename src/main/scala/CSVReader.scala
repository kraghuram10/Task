import scala.io.Source
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
object CSVReader {
  def apply(file: String): List[ContainerJob] = {
    val source = Source.fromFile(file)
    try {
      val lines = source.getLines().drop(1)
      val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
      lines.map { line =>
        val fields = line.split(",")
        ContainerJob(
          fields(0),
          fields(1),
          JobType.valueOf(fields(2)),
          LocalDateTime.parse(fields(3), formatter),
          fields(4).toInt,
          fields(5)
        )
      }.toList
    } finally {
      source.close()
    }
  }
}