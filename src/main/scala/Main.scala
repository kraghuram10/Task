import java.time.{LocalDate, LocalDateTime}

object Main {
  def main(args : Array[String]): Unit ={
    val jobs = CSVReader.apply("src/main/scala/container_logs.csv")
//    println(jobs)
    JobAnalytics.dailyBreakdown(jobs,LocalDate.of(2026, 11, 26))
    println("------------------")
    JobAnalytics.hourlyBreakdown(jobs,LocalDate.of(2026, 11, 26),6)
    JobAnalytics.countHourlyBreakdown(jobs,LocalDate.of(2026, 11, 25),6,JobType.valueOf("Load"))
    val start = LocalDateTime.of(2026, 11, 25, 6, 0)
    val end = LocalDateTime.of(2026, 11, 25, 18, 0)

    JobAnalytics.totalJobCountByType(jobs, start, end)
    println(JobAnalytics.hourlyJobsByBlock(jobs,LocalDate.of(2026, 11, 26),"2B"))
    JobAnalytics.blockData(jobs)
  }
}
